package com.h2t.study;

import com.h2t.study.entity.CacheNode;
import com.h2t.study.strategy.ExpireStrategy;
import com.h2t.study.strategy.impl.LazyExpireStrategy;
import com.h2t.study.strategy.impl.RegularExpireStrategy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 本地缓存
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/24 17:49
 */
public class LocalCache<K, V> {
    /**
     * 底层缓存结构
     */
    private LinkedHashMap<K, CacheNode<K, V>> localCache;

    /**
     * 负载因子
     */
    private final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 缓存过期清理策略
     */
    private ExpireStrategy<K, V> lazyExpireStrategy = new LazyExpireStrategy<>();

    private ExpireStrategy<K, V> regularExpireStrategy;

    private int maxCacheSize;

    /**
     * 构造函数
     *
     * @param expireStrategy 缓存失效策略实现类，针对的是定期失效缓存，传入null，定期失效缓存类为默认配置值
     * @param maxCacheSize    缓存最大允许存放的数量，缓存失效策略根据这个值触发
     */
    public LocalCache(int maxCacheSize, ExpireStrategy<K, V> expireStrategy) {
        //缓存最大容量为初始化的大小
        this.maxCacheSize = maxCacheSize;
        //缓存最大容量 => initialCapacity * DEFAULT_LOAD_FACTOR，避免扩容操作
        int initialCapacity = (int) Math.ceil(maxCacheSize / DEFAULT_LOAD_FACTOR) + 1;
        //accessOrder设置为true，根据访问顺序而不是插入顺序
        this.localCache = new LinkedHashMap<K, CacheNode<K, V>>(initialCapacity, DEFAULT_LOAD_FACTOR, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheNode<K, V>> eldest) {
                return size() > maxCacheSize;
            }
        };
        this.regularExpireStrategy = (expireStrategy == null ? new RegularExpireStrategy<>() : expireStrategy);
        //启动定时清除过期键任务
        regularExpireStrategy.removeExpireKey(localCache, null);
    }



    /**
     * 根据key获取缓存值
     *
     * @param key
     * @return
     */
    public synchronized V getValue(K key) {
        return lazyExpireStrategy.removeExpireKey(localCache, key);
    }

    /**
     * 缓存key-value
     *
     * @param key
     * @param value
     * @return
     */
    public synchronized V putValue(K key, V value) {
        CacheNode<K, V> cacheNode = new CacheNode<>();
        cacheNode.setKey(key);
        cacheNode.setValue(value);
        localCache.put(key, cacheNode);
        // 返回添加的值
        return value;
    }

    /**
     * 缓存key-value，包含过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间 expireTime时间后失效，默认时间单位为毫秒
     * @return
     */
    public synchronized V putValue(K key, V value, long expireTime) {
        CacheNode<K, V> cacheNode = new CacheNode<>();
        cacheNode.setKey(key);
        cacheNode.setValue(value);
        cacheNode.setGmtCreate(System.currentTimeMillis() + expireTime);
        localCache.put(key, cacheNode);
        // 返回添加的值
        return value;
    }

    /**
     * 删除key-value
     *
     * @param key
     * @return
     */
    public synchronized V removeKey(K key) {
        CacheNode<K, V> cacheNode = localCache.remove(key);
        return cacheNode != null ? cacheNode.getValue() : null;
    }

    /**
     * 清空缓存所有内容
     */
    public synchronized void clear() {
        localCache.clear();
    }

    /**
     * 设置某个键的过期时间
     *
     * @param key
     * @param expireTime 过期时间 expireTime时间后失效，默认时间单位为毫秒
     * */
    public synchronized void setExpireKey(K key, long expireTime) {
        if (localCache.get(key) != null) {
            localCache.get(key).setExpireTime(System.currentTimeMillis() + expireTime);
        }
    }

    /**
     * 获取本地缓存的大小
     *
     * @return
     */
    public synchronized int getLocalCacheSize() {
        return localCache.size();
    }
}

