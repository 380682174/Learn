package com.fish.learn.zookeeper;

import com.fish.learn.zookeeper.service.discovery.impl.ZkServiceDiscovery;
import com.fish.learn.zookeeper.service.registry.impl.ZkServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZookeeperApplication {

    private static final String SERVICE_NAME = "fish.com";

    private static final String SERVER_ADDRESS = "localhost:2181";

    public static void main(String[] args) {

        SpringApplication.run(ZookeeperApplication.class, args);

        ZkServiceRegistry registry = new ZkServiceRegistry();
        registry.init();
        registry.registry(SERVICE_NAME,SERVER_ADDRESS);

        ZkServiceDiscovery discovery = new ZkServiceDiscovery();
        discovery.init();
        discovery.discover(SERVICE_NAME);

        while (true){}

    }

}
