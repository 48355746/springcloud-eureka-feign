package com.ledo.eurekaclient.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "eureka-client-feign")
public interface UserRemote {
    @GetMapping("/user/{name}")
    String user(@PathVariable("name") String name);

}
