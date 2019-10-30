package com.h2t.study.entity;

/**
 * 本地缓存value值类型
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/24 18:33
 */
public class CacheNode<K, V> {
    /**
     * 保存的键
     */
    private K key;

    /**
     * 保存的值
     */
    private V value;

    /**
     * 保存时间
     */
    private long gmtCreate;

    /**
     * 过期时间，单位为毫秒，默认永久有效
     */
    private long expireTime = Long.MAX_VALUE;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
