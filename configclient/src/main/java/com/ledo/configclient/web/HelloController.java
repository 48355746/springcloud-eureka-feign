package com.ledo.configclient.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope// 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class HelloController {
    private final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Value("${hello}")
    private String hello;
    @GetMapping("hello")
    public String from(){
        logger.info("request two name is 11111111");
        try{
            Thread.sleep(1000000);
        }catch ( Exception e){
            logger.error(" hello two error",e);
        }

        return this.hello+"1111111111111";
    }
}
