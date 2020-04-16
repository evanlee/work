package com.example.demo.blockQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {
    /**
     *  同步阻塞队列，生产一个消费一个，不存储。
     * @param args
     */
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new SynchronousQueue();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + "\t  1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + "\t  2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "\t  3");
                blockingQueue.put("3");
            }catch (Exception e){
                e.printStackTrace();
            }
        },"aaa").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t "+blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t "+blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t "+blockingQueue.take());
            }catch (Exception e){
                e.printStackTrace();
            }
        },"bbb").start();
    }
}
