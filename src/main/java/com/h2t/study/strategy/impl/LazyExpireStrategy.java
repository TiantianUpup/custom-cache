package com.h2t.study.strategy.impl;

import com.h2t.study.entity.CacheNode;
import com.h2t.study.strategy.ExpireStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

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
     * @param localCache 本地缓存底层使用的存储结构
     * @param key 缓存的键
     * @return 过期的值
     */
    @Override
    public V removeExpireKey(LinkedHashMap<K, CacheNode<K, V>> localCache, K key) {
        CacheNode<K, V> baseCacheValue = localCache.get(key);
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

        logger.info("key:{}已过期，进行懒删除key操作", key);
        localCache.remove(key);
        return null;
    }
}
