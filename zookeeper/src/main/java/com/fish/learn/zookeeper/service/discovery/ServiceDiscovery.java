package com.fish.learn.zookeeper.service.discovery;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/4/30 21:01
 */
public interface ServiceDiscovery {

    /**
     * 服务发现
     * @param name
     * @return
     */
    String discover(String name);

}
