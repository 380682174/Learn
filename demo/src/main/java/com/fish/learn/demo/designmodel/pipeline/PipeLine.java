package com.fish.learn.demo.designmodel.pipeline;

/**
 * @Description: 管道接口
 * @Author devin.jiang
 * @CreateDate 2019/1/10 15:30
 */
public interface PipeLine {

    public Valve getFirst();

    public Valve getBasic();

    public void addValve(Valve valve);

    public void setBasic(Valve valve);

}
