package com.ledo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * 领导选举
 */
public class LeaderSelectorRunner implements CommandLineRunner {
    private static final String ZK_ADDRESS = "10.237.36.21:2181";
    private static final String ZK_PATH = "/zkleader";
    Logger log = LoggerFactory.getLogger(LeaderSelector.class);
    @Override
    public void run(String... args) throws Exception {
        LeaderSelectorListener listener=new LeaderSelectorListener() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                log.info(Thread.currentThread().getName() + " take leadership!");
                Thread.sleep(5000L);

                log.info(Thread.currentThread().getName() + " relinquish leadership!");
            }

            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

            }
        };
        new Thread(() -> {
            registerListener(listener);
        }).start();

        new Thread(() -> {
            registerListener(listener);
        }).start();

        new Thread(() -> {
            registerListener(listener);
        }).start();

       // Thread.sleep(Integer.MAX_VALUE);
    }
    private  void registerListener(LeaderSelectorListener listener) {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();


        // 3.Register listener
        LeaderSelector selector = new LeaderSelector(client, ZK_PATH, listener);
        selector.autoRequeue();
        try {
            log.info(String.valueOf(selector.getLeader()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        selector.start();
    }
}
