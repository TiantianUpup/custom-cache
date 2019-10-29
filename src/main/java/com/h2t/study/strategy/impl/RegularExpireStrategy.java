package com.h2t.study.strategy.impl;

import com.h2t.study.entity.CacheNode;
import com.h2t.study.strategy.ExpireStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 定期删除策略
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/25 16:09
 */
public class RegularExpireStrategy<K, V> implements ExpireStrategy<K, V> {
    Logger logger = LoggerFactory.getLogger(getClass());
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

    //get and set
    public long getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(long executeCount) {
        this.executeCount = executeCount;
    }

    public long getExecuteDuration() {
        return executeDuration;
    }

    public void setExecuteDuration(long executeDuration) {
        this.executeDuration = executeDuration;
    }

    public long getExecuteRate() {
        return executeRate;
    }

    public void setExecuteRate(long executeRate) {
        this.executeRate = executeRate;
    }

    /**
     * 清空过期Key-Value
     *
     * @param localCache 本地缓存底层使用的存储结构
     * @param key 缓存的键
     * @return 过期的值
     */
    @Override
    public V removeExpireKey(LinkedHashMap<K, CacheNode<K, V>> localCache, K key) {
        new Timer("remove-expire-key-task").schedule(new TimerTask() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<K> keyList = localCache.keySet().stream().collect(Collectors.toList());
                int size = keyList.size();
                Random random = new Random();

                for (int i = 0; i < executeCount; i++) {
                    K randomKey = keyList.get(random.nextInt(size));
                    if (localCache.get(randomKey).getExpireTime() - System.currentTimeMillis() < 0) {
                        logger.info("key:{}已过期，进行删除key操作", randomKey);
                        localCache.remove(randomKey);
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
