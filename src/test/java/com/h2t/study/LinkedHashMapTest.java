package com.h2t.study;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap测试类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/30 18:22
 */
public class LinkedHashMapTest {
    public static void main(String[] args) {
        LinkedHashMap<Integer, Integer> localCache = new LinkedHashMap<Integer, Integer>(4, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > 4;
            }
        };


        for (int i = 0; i < 10; i++) {
            localCache.put(i, i);
        }

        System.out.println(localCache.size());

        for (Map.Entry<Integer, Integer> map : localCache.entrySet()) {
            System.out.print(map.getKey() + " ");
        }
        System.out.println();

        localCache.put(100, 100);
        for (Map.Entry<Integer, Integer> map : localCache.entrySet()) {
            System.out.print(map.getKey() + " ");
        }
        System.out.println();
    }
}
