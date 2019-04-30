package com.fish.learn.zookeeper.service.registry.impl;

import com.fish.learn.zookeeper.service.constant.Constant;
import com.fish.learn.zookeeper.service.registry.ServiceRegistry;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/4/30 20:44
 */
public class ZkServiceRegistry implements ServiceRegistry {

    private String zkAddress = "localhost";

    private ZkClient zkClient;

    public void init() {

        zkClient = new ZkClient(zkAddress,
                Constant.ZK_SESSION_TIMEOUT,
                Constant.ZK_CONNECTION_TIMEOUT);
        System.out.println(">>> connect to zookeeper");

    }

    @Override
    public void registry(String serviceName, String serviceAddress) {

        //创建registry节点（持久）
        String registryPath = Constant.ZK_REGISTRY;
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
            System.out.println(">>> create registry node:" + registryPath);
        }

        //创建service节点（持久）
        String servicePath = registryPath + "/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            System.out.println(">>>create service node:" + servicePath);
        }

        //创建address节点（临时）
        String addressPath = servicePath + "/address-";
        String addressNode = zkClient.createEphemeralSequential(addressPath,serviceAddress);
        System.out.println(">>> create address node:" + addressNode);

    }

}
