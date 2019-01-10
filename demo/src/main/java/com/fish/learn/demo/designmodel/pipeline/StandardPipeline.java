package com.fish.learn.demo.designmodel.pipeline;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 15:50
 */
public class StandardPipeline implements PipeLine {

    protected Valve first = null;

    protected Valve basic = null;

    @Override
    public Valve getFirst() {
        return first;
    }

    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void addValve(Valve valve) {
        if (first == null) {
            first = valve;
            valve.setNext(basic);
        } else {
            Valve current = first;
            while (current != null) {
                if (current.getNext() == basic) {
                    current.setNext(valve);
                    valve.setNext(basic);
                    break;
                }
                current = current.getNext();
            }
        }
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic = valve;
    }
}
