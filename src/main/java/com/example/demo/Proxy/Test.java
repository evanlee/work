package com.example.demo.Proxy;

import com.example.demo.cglib.CglibProxy;
import com.example.demo.cglib.User;

public class Test {
    public static void main(String[] args) {
//        JDK动态代理只提供接口的代理，不支持类的代理。
        People p =(People)new JdkProxy().newProxy(new Chinese());
        p.sayHello();
//        不支持类的代理。
//        User p1 = (User) new JdkProxy().newProxy(new User());
//        p1.sayNo();
        System.out.println("--------------------");
//        CGLIB来动态代理目标类
        User proxy = (User) new CglibProxy().createCglibProxy(new User());
        proxy.sayNo();
        People proxy1 = (People) new CglibProxy().createCglibProxy(new Chinese());
        proxy1.sayHello();
    }


}
