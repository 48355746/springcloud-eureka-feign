package com.ledo.gatewayconsul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayconsulApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayconsulApplication.class, args);
    }

}
