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
     * 缓存过期清理策略
     */
    private ExpireStrategy<K, V> lazyExpireStrategy = new LazyExpireStrategy<>();

    private ExpireStrategy<K, V> regularExpireStrategy = new RegularExpireStrategy<>();

    /**
     * 缓存最大容量，超过这个容量，缓存将进行一次缓存失效处理
     * 通过map容量 * 0.8计算推断得到，避免扩容操作
     */
    private Long maxCacheSie = 800L;

    public LocalCache() {
        this.localCache = new LinkedHashMap<K, CacheNode<K, V>>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheNode<K, V>> eldest) {
                return size() > maxCacheSie;
            }
        };
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
        return localCache.remove(key) != null ? localCache.remove(key).getValue() : null;
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

