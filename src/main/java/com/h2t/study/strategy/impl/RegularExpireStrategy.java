package com.h2t.study.strategy.impl;

import com.h2t.study.dto.BaseCacheValue;
import com.h2t.study.strategy.ExpireStrategy;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 定期删除策略
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/25 16:09
 */
public class RegularExpireStrategy<K, V> implements ExpireStrategy<K, V> {
    /**
     * 定期任务每次执行删除操作的次数
     */
    private long executeCount = 100;

    /**
     * 定期任务执行时常 【1分钟】
     */
    private long executeDuration = 1000 * 60;

    /**
     * 定期任务执行的频率
     */
    private long executeRate = 1000 * 60 * 60;

    /**
     * 清空过期Key-Value
     *
     * @param localCache 本地缓存底层使用的存储结构
     * @param key 缓存的键
     * @return 过期的值
     */
    @Override
    public V removeExpireKey(ConcurrentHashMap<K, BaseCacheValue<V>> localCache, K key) {
        new Timer("remove-expire-key-task").schedule(new TimerTask() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<K> keyList = localCache.keySet().stream().collect(Collectors.toList());
                int size = keyList.size();
                Random random = new Random();

                for (int i = 0; i < executeCount; i++) {
                    K key = keyList.get(random.nextInt(size));
                    if (localCache.get(key).getExpireTime() - System.currentTimeMillis() < 0) {
                        localCache.remove(key);
                    }

                    //超时执行退出
                    if (System.currentTimeMillis() - start > executeDuration) {
                        break;
                    }
                }
            }
        }, executeRate); //1小时执行一次
        return null;
    }
}
