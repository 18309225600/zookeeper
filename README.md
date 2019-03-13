#zk 练习

zk 搭建：
    1.准备三台服务器
    2.安装好jdk
    3.下载zk解压包
    4.解压到/usr/local/apps
    5.进入zk的conf目录
    6.cp zoo_sample.cfg zoo.cfg
    7.编辑zoo.cfg
        7.1 修改数据目录  dataDir=/home/zkdata
        7.2 配置server信息
            server.1=192.168.5.137:2888:3888
            server.2=192.168.5.138:2888:3888
            server.3=192.168.5.139:2888:3888
    8.创建 /home/zkdata 目录
    9.进入/home/zkdata 然后 echo 1 > myid （机器的myid要不同）
    10.启动zkServer
    11.查看状态是否正常

遇到的两个坑：（遇到坑不要慌，查看日志：bin/zookeeper.out 里面有出错原因）
    1.都能正常启动，但是集群不能工作，（firewalld没有关闭），俩命令搞定
        1.1 systemctl stop firewalld
        1.2 chkconfig firewalld off
    2.其他俩机器能正常工作，一主一从，第三台不能正常工作（没有创建数据目录，目录里面没有myid文件，文件里面没有内容  都会导致）
        按步骤创建相应文件




两个modules功能：
    zk-crud:基本的java使用zkapi，完成增删改查及查询节点是否存在功能
    zk-server-client:使用监听，服务端节点创建临时且有序的节点，服务端节点上下线，客户端动态感知。