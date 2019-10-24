package com.h2t.study;

import com.h2t.study.dto.CacheDTO;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/24 17:49
 */
public class LocalCache {
    /**
     * 底层缓存结构
     */
    private ConcurrentHashMap<String, CacheDTO> localCache = new ConcurrentHashMap<>();
    /**
     * 缓存失效策略
     */
    private EliminateProxy eliminateProxy;

    /**
     * 根据key获取缓存值
     */
    public Object getValue(String key) {
        return null;
    }

    /**
     * 缓存key、value
     */
    public void putValue(String key, Object value, long expireTime) {
    }

    /**
     * 清空缓存所有内容
     */
    public void clear() {
    }
}

