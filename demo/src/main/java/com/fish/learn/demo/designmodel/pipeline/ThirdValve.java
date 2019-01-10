package com.fish.learn.demo.designmodel.pipeline;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 15:47
 */
public class ThirdValve implements Valve {

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
        handling = handling.replaceAll("zz", "yy");
        System.out.println("Third阀门处理完后：" + handling);
        getNext().invoke(handling);
    }
}
