package com.h2t.study;

import com.h2t.study.dto.BaseCacheValue;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Description
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/25 15:18
 */
public class Test {
    public static void main(String[] args) {
        Map<String, BaseCacheValue> map = new HashMap<>();
        BaseCacheValue<String> bcv1 = new BaseCacheValue();
        bcv1.setValue("tt1");

        BaseCacheValue<String> bcv2 = new BaseCacheValue();
        bcv2.setValue("tt2");

        map.put("kv", bcv1);
        map.put("kv", bcv2);

        System.out.println(map.size());

        for (Map.Entry<String, BaseCacheValue> m : map.entrySet()) {
            System.out.println(m.getKey() + ":" + m.getValue());
        }
    }
}
