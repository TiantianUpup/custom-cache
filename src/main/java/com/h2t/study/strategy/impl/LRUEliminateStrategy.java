package com.h2t.study.strategy.impl;

import com.h2t.study.dto.BaseCacheValue;
import com.h2t.study.strategy.EliminateStrategy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * LRU缓存淘汰策略
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/24 18:27
 */
public class LRUEliminateStrategy<K, V> implements EliminateStrategy<K, V> {

    /**
     * 根据缓存淘汰策略淘汰key
     *
     * @param localCache 本地缓存底层存储结构
     * @return 淘汰的缓存key
     */
    @Override
    public K removeKey(ConcurrentHashMap<K, BaseCacheValue<V>> localCache) {
        return null;
    }
}
