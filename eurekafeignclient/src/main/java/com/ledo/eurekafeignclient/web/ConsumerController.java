package com.ledo.eurekafeignclient.web;

import com.ledo.eurekafeignclient.remote.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    @Autowired
    HelloRemote helloRemote;
    @GetMapping("/hello")
    public String index()
    {
        return helloRemote.hello();
    }

    @GetMapping("/user/{name}")
    public String user(@PathVariable("name") String name)
    {
        return  name;
    }
}
