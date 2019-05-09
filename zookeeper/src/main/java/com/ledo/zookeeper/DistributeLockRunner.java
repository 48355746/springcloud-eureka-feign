package com.ledo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * 分布式锁测试
 */

public class DistributeLockRunner implements CommandLineRunner {
    private static final String ZK_ADDRESS = "10.237.36.21:2181";
    private static final String ZK_LOCK_PATH = "/zklock";
    Logger log = LoggerFactory.getLogger(DistributeLockRunner.class);
    @Override
    public void run(String... args) throws Exception {
        CuratorFramework client= CuratorFrameworkFactory.newClient(ZK_ADDRESS,new
                RetryNTimes(10,5000));
        client.start();
        log.info("zk client start successfully!");
        Thread t1=new Thread(()->{
            doWithLock(client);
        },"t1");
        Thread t2=new Thread(()->{
            doWithLock(client);
        },"t2");
        t1.start();
        t2.start();
    }

    private void doWithLock(CuratorFramework client) {
        InterProcessMutex lock=new InterProcessMutex(client,ZK_LOCK_PATH);
        try {
            log.info(Thread.currentThread().getName() + "进入执行");
            //在等待时间内获取锁，如果等待时间短，进入后可能会抛出异常     You do not own the lock: /zklock
            if (lock.acquire(1 , TimeUnit.SECONDS)) {
                log.info(Thread.currentThread().getName() + " hold lock");
                Thread.sleep(5000L);
                log.info(Thread.currentThread().getName() + " release lock");
            }
        } catch (Exception e) {
            log.error(Thread.currentThread().getName()+"获取锁失败");
            //e.printStackTrace();
        }finally {
            try {
                lock.release();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
