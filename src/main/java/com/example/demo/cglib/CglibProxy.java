package com.example.demo.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    // CGlib需要代理的目标对象
    private Object targetObject;

    public Object createCglibProxy(Object obj) {
        this.targetObject =obj;
        //创建一个动态类的对象 Enhancer是CGLIB的核心类通过它来创建代理对象
        Enhancer enhancer = new Enhancer();
        //设置父类，即设置需要进行功能增强的类
        enhancer.setSuperclass(obj.getClass());
        //设置回调方法
        enhancer.setCallback(this);
        //创建代理类并返回
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //创建切面类，即需要进行功能增强的类
        // Aspect aspect = new Aspect();
        //目标类方法调用之前调用功能增强
        //aspect.checkPermission();
        //调用被代理对象的方法
        Object intercept = methodProxy.invokeSuper(o, objects);
        System.out.println("-------- end ---------");
        return intercept;
    }

}
