package com.h2t.study.strategy.impl;

import com.h2t.study.dto.BaseCacheValue;
import com.h2t.study.strategy.ExpireStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 懒加载删除过期key-value策略
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/25 15:04
 */
public class LazyExpireStrategy<K, V> implements ExpireStrategy<K, V> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
            logger.info("key:{}对应的value不存在", key);
            return null;
        } else {
            //值存在并且未过期
            if (baseCacheValue.getExpireTime() - System.currentTimeMillis() > 0) {
                return baseCacheValue.getValue();
            }
        }

        logger.info("key:{}已过期，进行删除key操作", key);
        localCache.remove(key);
        return null;
    }
}
