package com.h2t.study;

import com.h2t.study.strategy.impl.RegularExpireStrategy;

/**
 * LocalCache测试类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/30 16:46
 */
public class LocalCacheTest {
    public static void main(String[] args) throws InterruptedException {
        RegularExpireStrategy<Integer, Integer> expireStrategy = new RegularExpireStrategy<>();
        expireStrategy.setExecuteRate(1); //每隔1分钟执行一次
        LocalCache<Integer, Integer> localCache = new LocalCache<>(4, expireStrategy);

        for (int i = 0; i < 16; i++) {
            localCache.putValue(i, i);
        }

        System.out.println("缓存大小：" + localCache.getLocalCacheSize());

        for (int i = 0; i < 16; i++) {
            if (localCache.getValue(i) != null) {
                System.out.print(localCache.getValue(i) + " ");
            }
        }

        System.out.println();


        localCache.setExpireKey(15, 1000); //1s过期
        Thread.sleep(1000);
        System.out.println(localCache.getValue(15));

        localCache.setExpireKey(14, 3000); //3s过期，定期删除策略删除


        Thread.sleep(60 * 1000);
        System.out.println("打印剩余在缓存中的值，正确打印结果为12 13");
        for (int i = 0; i < 16; i++) {
            if (localCache.getValue(i) != null) {
                System.out.println(i + "：" + localCache.getValue(i));
            }
        }

        //删除缓存
        localCache.removeKey(13);
        //存入并设置失效时间
        localCache.putValue(11, 11, 60 * 1000);
    }
}
