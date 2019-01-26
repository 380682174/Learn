package com.fish.learn.demo.designmodel.builder;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/26 13:48
 */
public class Director {

    /**
     * 指挥装机人员组装电脑
     * @param builder
     */
    public void Construct(Builder builder){

        builder.buildCPU();
        builder.buildMainboard();
        builder.buildHD();

    }
}
