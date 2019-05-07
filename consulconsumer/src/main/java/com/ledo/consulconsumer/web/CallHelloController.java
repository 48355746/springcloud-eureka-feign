package com.ledo.consulconsumer.web;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

@RestController
public class CallHelloController {
    Logger log= LoggerFactory.getLogger(CallHelloController.class);
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @GetMapping("callhello")
    public  String callHello()
    {
        ServiceInstance serviceInstance = loadBalancerClient.choose("service-producer");
        log.info("serviceUri"+serviceInstance.getUri());
        log.info("serviceName"+serviceInstance.getServiceId());
        String result=new RestTemplate().getForObject(serviceInstance.getUri().toString()+"/hello",String.class);
        return result;
    }
}
