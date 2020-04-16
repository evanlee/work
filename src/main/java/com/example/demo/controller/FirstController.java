package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @RequestMapping("/first")
    public Object first() {
        return "first";
    }

    @RequestMapping("/doError")
    public Object error() {
        return 1 / 0;
    }
}
