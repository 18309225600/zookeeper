package com.lhf.client;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lhf
 * @Description
 * @Date 2019/3/13 14:41
 * @Version 1.0
 **/
public class ClientDemo {

    private String connectString = "192.168.5.137:2181,192.168.5.138:2181,192.168.5.139:2181";
    private int sessionTimeout = 2000;
    private ZooKeeper zk = null;

    private final String group = "/servers/";

    private volatile  List<String> servers = null;

    public static void main(String []args) throws IOException, InterruptedException, KeeperException {
        ClientDemo client = new ClientDemo();

        //获取连接
        client.getConnection();

        //查看severs节点数据变化
        client.getServerList();

        //发起业务请求
        client.req();
    }

    private void req() throws InterruptedException {
        System.out.println("req...");
        Thread.sleep(Long.MAX_VALUE);
    }

    private void getServerList() throws KeeperException, InterruptedException, UnsupportedEncodingException {
        List<String> children = zk.getChildren("/servers", true);
        List<String> ss = new ArrayList<String>();
        for (String child:children){
            byte[] data = zk.getData(group + child, false, null);
            ss.add(new String(data,"utf-8"));
        }
        servers = ss;
        System.out.println(servers);
    }

    private void getConnection() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                try {
                    getServerList();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
