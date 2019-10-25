package com.h2t.study.dto;

/**
 * 本地缓存value值类型
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/24 18:33
 */
public class BaseCacheValue<T> {
    /**
     * 保存的值
     */
    private T value;

    /**
     * 保存时间
     */
    private long gmtCreate;

    /**
     * 过期时间，单位为毫秒，默认永久有效
     */
    private long expireTime = 0;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
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
