package com.ledo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

public class ZookeeperRegistRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.237.36.21:2181",
                new RetryNTimes(10, 5000));
        client.start();
        // 获取子节点，顺便监控子节点
        List<String> children = client.getChildren().usingWatcher(new CuratorWatcher() {
            public void process(WatchedEvent event) throws Exception
            {
                System.out.println("监控： " + event);
            }
        }).forPath("/");
        System.out.println(children);
        // 创建节点
             String result = client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath("/test", "Data".getBytes());
             System.out.println(result);

        // 设置节点数据
        client.setData().forPath("/test", "111".getBytes());
        client.setData().forPath("/test", "222".getBytes());
        // 删除节点
        System.out.println(client.checkExists().forPath("/test"));
        client.delete().withVersion(-1).forPath("/test");
        System.out.println(client.checkExists().forPath("/test"));
        client.close();
        System.out.println("OK！");

    }


}
