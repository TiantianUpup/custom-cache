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

    private int maxCacheSie;

    public LocalCache(int maxCacheSie, ExpireStrategy<K, V> expireStrategy) {
        //缓存最大容量为初始化的大小
        this.maxCacheSie = maxCacheSie;
        //缓存最大容量 => initialCapacity * DEFAULT_LOAD_FACTOR，避免扩容操作
        int initialCapacity = (int) Math.ceil(maxCacheSie / DEFAULT_LOAD_FACTOR) + 1;
        //accessOrder设置为true，根据访问顺序而不是插入顺序
        this.localCache = new LinkedHashMap<K, CacheNode<K, V>>(initialCapacity, DEFAULT_LOAD_FACTOR, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheNode<K, V>> eldest) {
                return size() > maxCacheSie;
            }
        };
        this.regularExpireStrategy = (expireStrategy == null ? new RegularExpireStrategy<>() : expireStrategy);
        //启动定时清除过期键任务
        regularExpireStrategy.removeExpireKey(localCache, null);
    }



    /**
     * 根据key获取缓存值
     */
    public V getValue(K key) {
        return lazyExpireStrategy.removeExpireKey(localCache, key);
    }

    /**
     * 缓存key-value
     */
    public V putValue(K key, V value) {
        CacheNode<K, V> cacheNode = new CacheNode<>();
        cacheNode.setKey(key);
        cacheNode.setValue(value);
        localCache.put(key, cacheNode);
        // 返回添加的值
        return value;
    }

    /**
     * 删除key-value
     */
    public V removeKey(K key) {
        CacheNode<K, V> cacheNode = localCache.remove(key);
        return cacheNode != null ? cacheNode.getValue() : null;
    }

    /**
     * 清空缓存所有内容
     */
    public void clear() {
        localCache.clear();
    }

    /**
     * 设置某个键的过期时间
     * */
    public void setExpireKey(K key, long expireTime) {
        if (localCache.get(key) != null) {
            localCache.get(key).setExpireTime(System.currentTimeMillis() + expireTime);
        }
    }
}

