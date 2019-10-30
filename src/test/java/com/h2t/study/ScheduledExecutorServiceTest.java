package com.h2t.study;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * TODO Description
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/30 11:37
 */
public class ScheduledExecutorServiceTest {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        //定时任务，executeRate分钟之后执行，默认1小时执行一次
        executor.scheduleAtFixedRate(new MyTask1(), 0, 1, TimeUnit.SECONDS);
    }

    private static class MyTask1 implements Runnable {
        @Override
        public void run() {
            System.out.println("something");
        }
    }
}
