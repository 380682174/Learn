package com.fish.learn.demo.designmodel.pipeline;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 15:33
 */
public class BasicValve implements Valve {

    protected Valve next = null;

    @Override
    public Valve getNext() {
        return next;
    }

    @Override
    public void setNext(Valve valve) {
        this.next = valve;
    }

    @Override
    public void invoke(String handling) {
        handling=handling.replaceAll("aa", "bb");
        System.out.println("基础阀门处理完后：" + handling);
    }
}
