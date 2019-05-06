package com.ledo.eurekafeignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class EurekafeignclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekafeignclientApplication.class, args);
    }

}
