package com.ledo.eurekafeignclient.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
@Service
@FeignClient(name = "eureka-client")
public interface HelloRemote {
    @GetMapping("/hello")
    public String hello();
}
