package com.h2t.study;

import com.h2t.study.strategy.EliminateStrategy;
import com.h2t.study.strategy.ExpireStrategy;
import com.h2t.study.strategy.impl.LazyExpireStrategy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/24 17:49
 */
public class LocalCache<K, V> {
    /**
     * 底层缓存结构
     */
    private ConcurrentHashMap<K, CacheNode<K, V>> localCache = new ConcurrentHashMap<>(512);

    /**
     * 缓存失效策略
     */
    private EliminateStrategy eliminateStrategy;

    /**
     * 缓存过期清理策略
     */
    private ExpireStrategy<K, V> expireStrategy = new LazyExpireStrategy<>();

    /**
     * 缓存最大容量，超过这个容量，缓存将进行一次缓存失效处理
     * 通过map容量 * 0.8计算推断得到，避免扩容操作
     */
    private Long maxCacheSie = 800L;

    /**
     * 首节点
     */
    private CacheNode<K, V> first;

    /**
     * 尾节点
     */
    private CacheNode<K, V> last;

    /**
     * 根据key获取缓存值
     */
    public V getValue(K key) {
        //1.判断缓存是否过期，过期则删除，返回null
        //删除 删除map中的值，删除双向链表之间的关系
        //2.若不过期，将节点移到队首，返回值
        //return expireStrategy.removeExpireKey(localCache, key);
        return null;
    }

    /**
     * 缓存key-value
     */
    public V putValue(K key, V value) {
        //1. 判断键值对存不存在，不存在则不需要进行缓存淘汰
        CacheNode<K, V> cacheNode = localCache.get(key);
        if (cacheNode == null) {
            //1.1 判断是否需要进行缓存淘汰
            if (localCache.size() >= maxCacheSie) {
                //1.1.1 删除双向链表关系 todo

                //1.1.2 删除map中的值
                eliminateStrategy.removeKey(localCache);
            }

            //1.2 创建新节点
            cacheNode = new CacheNode<>();
        }

        //2. 赋值
        cacheNode.key = key;
        cacheNode.value = value;
        //3. 将新节点添加到队首
        moveToHead(cacheNode);
        //4. 将节点插入map中
        localCache.put(key, cacheNode);

        // 返回添加的值
        return value;
    }

    /**
     * 删除key-value
     */
    public V removeKey(K key) {
        return null;
    }

    /**
     * 清空缓存所有内容
     */
    public void clear() {
        localCache.clear();
    }

    /**
     * 设置某个键的过期时间
     * */
    public void setExpireKey(K key, long expireTime) {
        //localCache.get(key).setExpireTime(System.currentTimeMillis() + expireTime);
    }

    /**
     * 移动节点为首节点
     */
    private void moveToHead(CacheNode<K, V> cacheNode) {
        if (cacheNode == first) {
            return;
        }

        if (cacheNode.prev != null) {
            cacheNode.prev.next = cacheNode.next;
        }

        if (cacheNode.next != null) {
            cacheNode.next.prev = cacheNode.prev;
        }

        if (cacheNode == last) {
            last = cacheNode.prev;
        }
        if (first != null) {
            cacheNode.next = first;
            first.prev = cacheNode;
        }

        first = cacheNode;
        cacheNode.prev = null;
        if (last == null) {
            last = first;
        }
    }
}

