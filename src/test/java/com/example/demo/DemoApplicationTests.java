package com.example.demo;

import com.example.demo.util.RedisUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import static java.lang.Math.floor;
import static java.lang.Thread.sleep;


@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1() throws Exception {

        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
        //存储数据到列表中
        redisTemplate.opsForList().leftPush("site-list", "Runoob");
        redisTemplate.opsForList().leftPush("site-list", "Google");
        redisTemplate.opsForList().leftPush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = redisTemplate.opsForList().range("site-list", 0, 2);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("列表项为: " + list.get(i));
        }
    }

    @Test
    void contextLoads() {
        Jedis jedis = new Jedis("localhost");
        boolean b = RedisUtils.tryGetDistributedLock(jedis, "23", "123", 2);
        System.out.println(b);
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean c = RedisUtils.tryGetDistributedLock(jedis, "23", "124", 2);
        System.out.println(b);
        boolean b0 = RedisUtils.releaseDistributedLock(jedis, "23", "123");
        System.out.println(b0 + "--b0");
        System.out.println("c=" + c);
        boolean b1 = RedisUtils.releaseDistributedLock(jedis, "23", "124");
        System.out.println("b1=" + b1);
    }

    @Test
    public void xx() {
        String s = "abc";
        char[] chars = s.toCharArray();
        String ret = "";
        for (int i = chars.length; i > 0; i--) {
            ret = ret + chars[i - 1];
        }
        System.out.println(ret);
    }

    /**
     * minor GC(young GC):当年轻代中eden区分配满的时候触发[值得一提的是因为young GC后部分存活的对象会
     * 已到老年代(比如对象熬过15轮)，所以过后old gen的占用量通常会变高]
     * <p>
     * full GC:
     * ①手动调用System.gc()方法 [增加了full GC频率，不建议使用而是让jvm自己管理内存，
     * 可以设置-XX:+ DisableExplicitGC来禁止RMI调用System.gc]
     * ②发现perm gen（如果存在永久代的话)需分配空间但已经没有足够空间
     * ③老年代空间不足，比如说新生代的大对象大数组晋升到老年代就可能导致老年代空间不足。
     * ④CMS GC时出现Promotion Faield[pf]
     * ⑤统计得到的Minor GC晋升到旧生代的平均大小大于老年代的剩余空间。
     * 这个比较难理解，这是HotSpot为了避免由于新生代晋升到老年代导致老年代空间不足而触发的FUll GC。
     * 比如程序第一次触发Minor GC后，有5m的对象晋升到老年代，姑且现在平均算5m，那么下次Minor GC发生时，
     * 先判断现在老年代剩余空间大小是否超过5m，如果小于5m，则HotSpot则会触发full GC(这点挺智能的)
     * <p>
     * Promotion Faield:minor GC时 survivor space放不下[满了或对象太大]，对象只能放到老年代，而老年代也放不下会导致这个错误。
     * Concurrent Model Failure:cms时特有的错误，因为cms时垃圾清理和用户线程可以是并发执行的，如果在清理的过程中
     * 可能原因：
     * 1 cms触发太晚，可以把XX:CMSInitiatingOccupancyFraction调小[比如-XX:CMSInitiatingOccupancyFraction=70 是指设定CMS在对内存占用率达到70%的时候开始GC(因为CMS会有浮动垃圾,所以一般都较早启动GC)]
     * 2 垃圾产生速度大于清理速度，可能是晋升阈值设置过小，Survivor空间小导致跑到老年代，eden区太小，存在大对象、数组对象等情况
     * 3.空间碎片过多，可以开启空间碎片整理并合理设置周期时间
     */
    @Test
    public void yy() {
        System.out.println(floor(6.1) % 2 == 0);

        List list = new ArrayList();
        list.add(1);
        list.add(2);
        for (int i = 0; i < list.size(); i++) {
            Integer l = (Integer) list.get(i);
            if (l % 2 == 0) {
                System.out.println(list.get(i));
            }
        }
    }

    /**
     * ConcurrentSkipListMap是线程安全的有序的哈希表，适用于高并发的场景。
     * ConcurrentSkipListMap和TreeMap，它们虽然都是有序的哈希表。但是，第一，它们的线程安全机制不同，
     * TreeMap是非线程安全的，而ConcurrentSkipListMap是线程安全的。第二，ConcurrentSkipListMap是通过跳表实现的，
     * 而TreeMap是通过红黑树实现的。
     * <p>
     * 在4线程1.6万数据的条件下，ConcurrentHashMap 存取速度是ConcurrentSkipListMap 的4倍左右。
     * 但ConcurrentSkipListMap有几个ConcurrentHashMap 不能比拟的优点：
     * 1、ConcurrentSkipListMap 的key是有序的。
     * 2、ConcurrentSkipListMap 支持更高的并发。ConcurrentSkipListMap 的存取时间是log（N），和线程数几乎无关。也就是说在数据量一定的情况下，并发的线程越多，ConcurrentSkipListMap越能体现出他的优势。
     * 在非多线程的情况下，应当尽量使用TreeMap。此外对于并发性相对较低的并行程序可以使用Collections.synchronizedSortedMap将TreeMap进行包装，也可以提供较好的效率。对于高并发程序，应当使用ConcurrentSkipListMap，能够提供更高的并发度。
     * 所以在多线程程序中，如果需要对Map的键值进行排序时，请尽量使用ConcurrentSkipListMap，可能得到更好的并发度。
     * 注意，调用ConcurrentSkipListMap的size时，由于多个线程可以同时对映射表进行操作，所以映射表需要遍历整个链表才能返回元素个数，这个操作是个O(log(n))的操作。
     */
    @Test
    public void xsdl() {
        ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();

    }
}
