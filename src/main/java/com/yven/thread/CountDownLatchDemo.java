package com.yven.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch----很适合用来将一个任务分为n个独立的部分，等这些部分都完成后继续接下来的任务
 */
public class CountDownLatchDemo {

    final static SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(2);// 计数器为2
        Worker worker1 = new Worker("A",5000,latch);
        Worker worker2 = new Worker("B",8000,latch);
        worker1.start();
        worker2.start();
        latch.await();// 阻塞！等待所有工人完成工作，此处必须执行latch.countDown()之后减为 0 ，才能被能被唤醒
        System.out.println("all work done at "+sdf.format(new Date()));



    }

    static class Worker extends Thread{
        String workerName;
        int workTime ;
        CountDownLatch latch;
        public Worker(String workerName,int workTime,CountDownLatch latch){
            this.latch = latch;
            this.workerName = workerName;
            this.workTime = workTime;
        }

        @Override
        public void run() {
            System.out.println("Worker "+workerName+" begin work..."+sdf.format(new Date()));
            doWork();
            System.out.println("Worker "+workerName+" end work..."+sdf.format(new Date()));
            latch.countDown();// 完成工作，计数器减去1，变为 0 之后，可以唤醒latch.await()
        }

        private void doWork() {
            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
