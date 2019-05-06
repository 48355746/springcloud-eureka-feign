package com.ledo.eurekafeignclient.remote;

import org.springframework.stereotype.Component;

@Component
public class HelloRemoteHystrix implements HelloRemote {
    @Override
    public String hello() {
        return "hello failed";
    }
}
