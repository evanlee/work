package com.example.demo.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    /**
     * 一个初始值为零，两个线程对其交互操作，一个加1，一个减一，来五轮
     *
     * @param args
     */
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
    class ShareData{
        private int number =0;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        public void increment(){
            lock.lock();
            try{
                //1.判断
                while(number !=0){
                    //等待，不能生产
                    condition.await();
                }
                //2.干活
                number++;
                System.out.println(Thread.currentThread().getName()+"\t"+number);
                //3.通知唤醒
                condition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }

        public void decrement(){
            lock.lock();
            try{
                //1.判断
                while(number ==0){
                    //等待，不能生产
                    condition.await();
                }
                //2.干活
                number--;
                System.out.println(Thread.currentThread().getName()+"\t"+number);
                //3.通知唤醒
                condition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
    }
