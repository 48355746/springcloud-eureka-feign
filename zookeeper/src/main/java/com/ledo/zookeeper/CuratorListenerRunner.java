package com.ledo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CuratorListenerRunner implements CommandLineRunner {
    private static final String ZK_ADDRESS = "10.237.36.21:2181";
    private static final String ZK_LISTEN_PARENT_PATH = "/zklisten";
    private static final String ZK_LISTEN_PATH = "/zklisten/cnode";
    private static final String ZK_LISTEN_PATH1 = "/zklisten/cnode1";
    Logger log = LoggerFactory.getLogger(DistributeLockRunner.class);

    @Override
    public void run(String... args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDRESS).sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        if (client.checkExists().forPath(ZK_LISTEN_PATH) != null) {
            client.delete().forPath(ZK_LISTEN_PATH);
        }
        client.create().creatingParentsIfNeeded().forPath(ZK_LISTEN_PATH, "hello".getBytes());
        if (client.checkExists().forPath(ZK_LISTEN_PATH1) != null) {
            client.delete().forPath(ZK_LISTEN_PATH1);
        }
        client.create().creatingParentsIfNeeded().forPath(ZK_LISTEN_PATH1, "hello".getBytes());
        //在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
        ExecutorService pool = Executors.newFixedThreadPool(2);
        //针对某一节点进行监听
        final NodeCache nodeCache = new NodeCache(client, ZK_LISTEN_PATH, false);
        nodeCache.start(true);
        // 监听数据节点的变化情况
        nodeCache.getListenable().addListener(() -> log.info("Node data is changed,new data:" + new String(nodeCache.getCurrentData().getData())), pool);
        //监听子节点的变化情况
        final PathChildrenCache childrenCache = new PathChildrenCache(client, ZK_LISTEN_PARENT_PATH, true);
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        childrenCache.getListenable().addListener((curatorFramework, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    log.info("Child_added:" + event.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    log.info("Child_removed:" + event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    log.info("child_updated:" + event.getData().getPath());
                    break;
                default:
                    break;
            }
        }, pool);
        client.setData().forPath(ZK_LISTEN_PATH, "world".getBytes());
        Thread.sleep(10000);
        client.setData().forPath(ZK_LISTEN_PATH, "again".getBytes());
        client.setData().forPath(ZK_LISTEN_PATH1, "world1".getBytes());
        Thread.sleep(10000);
        pool.shutdown();
        client.close();
    }
}
