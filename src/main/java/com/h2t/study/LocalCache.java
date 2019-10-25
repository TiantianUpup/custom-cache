package com.h2t.study;

import com.h2t.study.dto.BaseCacheValue;
import com.h2t.study.strategy.EliminateStrategy;
import com.h2t.study.strategy.ExpireStrategy;
import com.h2t.study.strategy.impl.LazyExpireStrategy;

import java.util.concurrent.ConcurrentHashMap;

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
    private ConcurrentHashMap<K, BaseCacheValue<V>> localCache = new ConcurrentHashMap<>(512);

    /**
     * 缓存失效策略
     */
    private EliminateStrategy eliminateStrategy;

    /**
     * 缓存过期清理策略
     */
    private ExpireStrategy<K, V> expireStrategy = new LazyExpireStrategy<>();

    /**
     * 缓存最大容量，超过这个容量，缓存将进行一次缓存失效处理
     */
    private Long maxCacheSie = 1000L;

    /**
     * 根据key获取缓存值
     */
    public V getValue(K key) {
        return expireStrategy.removeExpireKey(localCache, key);
    }

    /**
     * 缓存key-value
     */
    public V putValue(K key, V value) {
        BaseCacheValue<V> baseCacheValue = new BaseCacheValue<>();
        baseCacheValue.setValue(value);
        baseCacheValue.setGmtCreate(System.currentTimeMillis());
        BaseCacheValue<V> bcv = localCache.put(key, baseCacheValue);
        return bcv == null ? null : bcv.getValue();
    }

    /**
     * 删除key-value
     */
    public V removeKey(K key) {
        BaseCacheValue<V> baseCacheValue = localCache.remove(key);
        return baseCacheValue == null ? null : baseCacheValue.getValue();
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
        localCache.get(key).setExpireTime(System.currentTimeMillis() + expireTime);
    }
}

