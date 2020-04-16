package com.example.demo.qrrxReference;

import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * created by liwenqiang 2020/4/14.
 */
@RestController
public class PhantomReferenceController {
//    1.虚引用需要java.lang.ref.PhantomReference类来实现。
//    2.形如虚设，它不会决定对象的生命周期。
//    3.如果一个对象持有虚引用，那么它就和没有任何一样，
//    在任何时候都可能被垃圾回收器回收， 它不能单独使用也不能通过它来访问对象，
//    虚引用必须和引用队列（ReferenceQueue）联合使用。
//    4.虚引用的主要作用是跟踪对象被垃圾回收的状态，仅仅是提供了一种确保对象被
//    finalize以后，做某些事情的机制。PhantomReference的get方法总是返回null，
//    因此无法访问对应的引用对象。其意义在于说明一个对象已经进入finalization阶段，
//    可以被gc回收，用来实现比finalization机制更灵活的回收操作。

    public static void main(String[] args) {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o1, referenceQueue);
        System.out.println(o1); //java.lang.Object@1540e19d        
        System.out.println(phantomReference.get());//null
        System.out.println(referenceQueue.poll());//null
    }

}
