package com.fish.learn.zookeeper.service.discovery.impl;

import com.fish.learn.zookeeper.service.constant.Constant;
import com.fish.learn.zookeeper.service.discovery.ServiceDiscovery;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/4/30 21:02
 */
public class ZkServiceDiscovery implements ServiceDiscovery {

    private String zkAddress = "localhost";

    private final List<String> addressCache = new CopyOnWriteArrayList<>();

    private ZkClient zkClient;

    public void init() {

        zkClient = new ZkClient(zkAddress,
                Constant.ZK_SESSION_TIMEOUT,
                Constant.ZK_CONNECTION_TIMEOUT);
        System.out.println(">>> connect to zookeeper");

    }

    @Override
    public String discover(String name) {

        try {
            String servicePath = Constant.ZK_REGISTRY + "/" + name;

            //获取服务节点
            if (!zkClient.exists(servicePath)) {
                throw new RuntimeException(String.format(">>>can't find any service node on path {}",servicePath));
            }

            //从本地缓存获取某个服务地址
            String address;
            int addressCacheSize = addressCache.size();
            if (addressCacheSize > 0) {
                if (addressCacheSize == 1) {
                    address = addressCache.get(0);
                } else {
                    address = addressCache.get(ThreadLocalRandom.current().nextInt(addressCacheSize));
                }
                System.out.println(">>>get only address node:" + address);

                //从zk服务注册中心获取某个服务地址
            } else {
                List<String> addressList = zkClient.getChildren(servicePath);
                addressCache.addAll(addressList);

                //监听servicePath下的子文件是否发生变化
                zkClient.subscribeChildChanges(servicePath,(parentPath,currentChilds)->{
                    System.out.println(">>>servicePath is changed:" + parentPath);
                    addressCache.clear();
                    addressCache.addAll(currentChilds);

                });

                if (CollectionUtils.isEmpty(addressList)) {
                    throw new RuntimeException(String.format(">>>can't find any address node on path {}", servicePath));
                }

                int nodeSize = addressList.size();
                if (nodeSize == 1) {
                    address = addressList.get(0);
                } else {

                    //如果多个，则随机取一个
                    address = addressList.get(ThreadLocalRandom.current().nextInt(nodeSize));
                }
                System.out.println(">>>get address node:" + address);

            }

            //获取IP和端口号
            String addressPath = servicePath + "/" + address;
            String hostAndPort = zkClient.readData(addressPath);

            return hostAndPort;

        } catch (Exception e) {
            System.out.println(">>> service discovery exception: " + e.getMessage());
            zkClient.close();
        }

        return null;

    }

}
