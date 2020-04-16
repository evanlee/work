package com.example.demo.gbf;

import com.example.demo.util.CountryEnum;
import org.apache.lucene.util.NamedThreadFactory;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class CountDownLatchDemo {
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                1L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
    //CountDownLatch 每执行完一个减一，直到值为0；
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 6,
                1L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("ACCEPT-POOL"));

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i < 11; i++) {
//            new Thread(()->{
//                System.out.println(Thread.currentThread().getName()+"\t 结束");
//                countDownLatch.countDown();
//            }, CountryEnum.foreach_enum(i).getRetMsg()).start();
            threadPoolExecutor.execute(()->{
                System.out.println(Thread.currentThread().getName()+"\t 结束");
                countDownLatch.countDown();
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"\t ******最终结束");
        threadPoolExecutor.shutdown();

    }
}
