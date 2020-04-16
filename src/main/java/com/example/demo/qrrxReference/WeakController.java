package com.example.demo.qrrxReference;

import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * created by liwenqiang 2020/4/14.
 */
@RestController
public class WeakController {

    public static void main(String args[]){
        Object o1 = new Object();
        //弱引用-- 只要垃圾回收机制一运行，不管JVM的内存空间是否足够，
        // 都会回收该对象占用的内存。
        WeakReference weakReference = new WeakReference(o1);
        o1=null;
        System.gc();
        System.out.println(o1);
        System.out.println(weakReference.get());

    }
}
