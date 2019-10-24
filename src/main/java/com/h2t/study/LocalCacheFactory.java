package com.h2t.study;

/**
 * LocalCache工厂类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/24 18:05
 */
public class LocalCacheFactory {
    /**
     * 使用懒汉式的单例模式，简单又安全
     */
    private static final LocalCache localCache = new LocalCache();

    public static LocalCache getLocalCache() {
        return localCache;
    }
}
