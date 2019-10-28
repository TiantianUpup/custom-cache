package com.h2t.study.strategy;

import com.h2t.study.dto.BaseCacheValue;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存淘汰策略
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/24 18:25
 */
public interface EliminateStrategy<K, V> {
    /**
     * 根据缓存淘汰策略淘汰key
     *
     * @param localCache 本地缓存底层存储结构
     * @return 淘汰的缓存key
     */
    K removeKey(ConcurrentHashMap<K, BaseCacheValue<V>> localCache);
}
