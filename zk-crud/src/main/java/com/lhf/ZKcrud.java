package com.lhf;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Author lhf
 * @Description
 * @Date 2019/3/13 11:25
 * @Version 1.0
 **/
public class ZKcrud {

    private String connectString = "192.168.5.137:2181,192.168.5.138:2181,192.168.5.139:2181";
    private int sessionTimeout = 2000;
    private ZooKeeper zkClient = null;

    @Before
    public void connect() throws IOException {
        zkClient=new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event.getPath()+"---"+event.getType());
            }
        });
    }

    //增
    @Test
    public void createData() throws KeeperException, InterruptedException {
        String create = zkClient.create("/app2", "app2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(create);
    }

    //是否存在
    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/app2", false);
        System.out.println(stat==null?"不存在":"存在");
    }

    //删
    @Test
    public void delData() throws KeeperException, InterruptedException {
        zkClient.delete("/app2",-1);
    }

    //改
    @Test
    public void updateData() throws KeeperException, InterruptedException {
        Stat stat = zkClient.setData("/app1", "update".getBytes(), -1);
        System.out.println(stat);
    }

    //查
    @Test
    public void selectData() throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] data = zkClient.getData("/app1", false, null);
        System.out.println(new String(data,"utf-8"));
    }
}
