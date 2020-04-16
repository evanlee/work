package com.example.demo.qrrxReference;

import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.SoftReference;
import java.util.WeakHashMap;

/**
 * created by liwenqiang 2020/4/14.
 */
@RestController
public class TestReferenceController {

//    public static void main(String args[]){
//        Object o1 = new Object();
//        //软引用-- 内存够用时就不回收，不够就回收
//        SoftReference softReference = new SoftReference(o1);
//        o1=null;
//        System.gc();
//        System.out.println(o1);
//        System.out.println(softReference.get());
//
//    }

//    软弱引用适用场景
//    假如有一个引用需要读取大量的本地图片
//    存在问题：
//    如果每次读取图片都从硬盘读取则会严重影响性能。
//    如果一次性全部加载到内存中有可能造成内存溢出。>解决思路：>
//    用一个HashMap来保存图片的路径和相应图片对象关联的软引用之间的映射关系，
//    在内存不足时，JVM会自动回收这些缓存图片对象所占用的空间，
//    从而有效地避免了OOM的问题。

    public static void  main(String[] args)  {
        WeakHashMap<Integer,String> weakHashMap  =  new  WeakHashMap<>();
        Integer  key  =  new  Integer(1);
        weakHashMap.put(key,"测试1");
        System.out.println(weakHashMap);        //{1=测试1}
        key=null;
        System.out.println(weakHashMap);// {1=测试1}
        System.gc();
        System.out.println(weakHashMap+"\t"+weakHashMap.size());

    }
}
