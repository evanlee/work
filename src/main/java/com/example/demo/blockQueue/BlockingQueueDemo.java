package com.example.demo.blockQueue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        //阻塞队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        /*System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //检查队列的队首，返回队首元素
        System.out.println(blockingQueue.element());
        //移除按fifo
        System.out.println(blockingQueue.remove());*/

        /*//特殊值
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("x"));
        System.out.println(blockingQueue.peek());
        System.out.println(blockingQueue.poll());*/

       /*
            阻塞
       try {
            blockingQueue.put("a");
            blockingQueue.put("a");
            blockingQueue.put("a");
            System.out.println("===============");
//            blockingQueue.put("x");
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

       try {
           System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
           System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
           System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));
           System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
           System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
           System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
           System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
       }catch (InterruptedException e){
           e.printStackTrace();
       }
    }
}
