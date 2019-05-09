package com.ledo.eventbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@RefreshScope
public class EventbusApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventbusApplication.class, args);
    }

}
