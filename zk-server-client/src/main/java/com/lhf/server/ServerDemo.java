package com.lhf.server;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @Author lhf
 * @Description
 * @Date 2019/3/13 14:41
 * @Version 1.0
 **/
public class ServerDemo {

    private String connectString = "192.168.5.137:2181,192.168.5.138:2181,192.168.5.139:2181";
    private int sessionTimeout = 2000;
    private ZooKeeper zk = null;

    private final String group = "/servers/";

    public static void main(String []args) throws IOException, KeeperException, InterruptedException {
        ServerDemo server = new ServerDemo();
        //获取连接
        server.getConnection();
        //注册服务信息
        server.registerServer(args[0]);
        //等待客户端请求
        server.waitReq(args[0]);
    }

    private void waitReq(String host) throws InterruptedException {
        System.out.println(host+"等待客户端请求..");
        Thread.sleep(Long.MAX_VALUE);
    }

    private void registerServer(String host) throws KeeperException, InterruptedException {
        zk.create(group+"server",host.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    private void getConnection() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event.getPath()+"---"+event.getType());
            }
        });
    }
}
