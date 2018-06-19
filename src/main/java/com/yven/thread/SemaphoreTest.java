package com.yven.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore---通过控制操作系统的信号量数目来控制并发，比控制线程并发数粒度更细。

 管理固定数值的信号量，用以控制并发的数量。把需要并发的代码放在acquire、release之间即可。

 acquire获取信号，release释放信号。如果Semaphore管理一个信号量，就是互斥锁
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);
        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        //获取许可
                        semp.acquire();
                        System.out.println("Accessing: " + NO);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally{
                        //释放
                        semp.release();
                        System.out.println("-----------------"+semp.availablePermits());
                    }
                }
            };
            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();
    }
}
