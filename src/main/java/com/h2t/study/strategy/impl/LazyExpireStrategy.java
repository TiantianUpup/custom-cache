package com.h2t.study.strategy.impl;

import com.h2t.study.dto.BaseCacheValue;
import com.h2t.study.strategy.ExpireStrategy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 懒加载删除过期key-value策略
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/25 15:04
 */
public class LazyExpireStrategy<K, V> implements ExpireStrategy<K, V> {

    /**
     * 清空过期Key-Value
     *
     * @param localCache
     * @param key
     */
    @Override
    public V removeExpireKey(ConcurrentHashMap<K, BaseCacheValue<V>> localCache, K key) {
        BaseCacheValue<V> baseCacheValue = localCache.get(key);
        //值不存在
        if (baseCacheValue == null) {
            return null;
        } else {
            //值存在并且未过期
            if (baseCacheValue.getExpireTime() - System.currentTimeMillis() > 0) {
                return baseCacheValue.getValue();
            }
        }

        localCache.remove(key);
        return null;
    }
}
