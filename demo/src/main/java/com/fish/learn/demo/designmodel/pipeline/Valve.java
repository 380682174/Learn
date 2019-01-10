package com.fish.learn.demo.designmodel.pipeline;

/**
 * @Description: 阀门接口
 * @Author devin.jiang
 * @CreateDate 2019/1/10 15:26
 */
public interface Valve {

    public Valve getNext();

    public void setNext(Valve valve);

    public void invoke(String handling);

}
