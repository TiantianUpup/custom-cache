package com.h2t.study;

import java.util.Random;

/**
 * Random测试类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/30 16:56
 */
public class RandomTest {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            System.out.println(random.nextInt(100));
        }
    }
}
