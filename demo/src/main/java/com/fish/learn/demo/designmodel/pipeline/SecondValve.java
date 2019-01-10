package com.fish.learn.demo.designmodel.pipeline;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 15:38
 */
public class SecondValve implements Valve {

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
        handling = handling.replaceAll("11", "22");
        System.out.println("Second阀门处理完后：" + handling);
        getNext().invoke(handling);
    }
}
