package com.h2t.study;

/**
 * 缓存节点值
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/28 16:04
 */
public class CacheNode<K, V> {
    /**
     * 先序节点
     */
    CacheNode<K, V> prev;

    /**
     * 后序节点
     */
    CacheNode<K, V> next;

    /**
     * 缓存的键
     */
    K key;

    /**
     * 缓存的值
     */
    V value;
}
