package com.h2t.study;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TODO Description
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/30 10:39
 */
public class TimerTest {
    public static void main(String[] args) {
        System.out.println("执行定时任务");
        new Timer().schedule(new MyTimerTask(), 0, 1000);
        System.out.println("影响后面的执行迈");
        System.out.println("先做小测验看看。。。");
    }

    static class MyTimerTask extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            System.out.println("do something regular");
        }
    }
}
