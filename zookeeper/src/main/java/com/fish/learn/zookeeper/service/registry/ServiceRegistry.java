package com.fish.learn.zookeeper.service.registry;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/4/30 20:41
 */
public interface ServiceRegistry {

    /**
     * 注册服务
     * @param serviceName
     * @param serviceAddress
     */
    void registry(String serviceName, String serviceAddress);

}
