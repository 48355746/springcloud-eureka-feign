package com.ledo.eurekaclient.web;

import com.ledo.eurekaclient.remote.UserRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloworldController {
    @Autowired
    UserRemote userRemote;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "hello world";
    }


    @GetMapping("/user/{name}")
    public String user(@PathVariable("name") String name) {
        return "hello" + userRemote.user(name);
    }
}
