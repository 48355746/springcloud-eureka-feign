package com.ledo.consulproducer.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public  String hello() {
        return "hello world consul";
    }
    @GetMapping("/fallback")
    public String fallback(){
        return "容断了";
    }
}


