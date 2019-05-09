package com.ledo.consulproducer.web;

import com.ecwid.consul.v1.QueryParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

import javax.naming.Name;
import javax.websocket.server.PathParam;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public  String hello(String foo) {
        return "hello world consul222" + foo;
    }

}


