package com.example.demo.controller;

import com.example.demo.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/first")
    public Object first() {
        redisTemplate.opsForValue().set("kk", "kk");
        redisUtil.set("kk", "mm");
        return redisTemplate.opsForValue().get("kk");
    }


    @RequestMapping("/doError")
    public Object error() {
        return 1 / 0;
    }
}
