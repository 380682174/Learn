package com.fish.learn.demo.designmodel.responsibilitychain;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 17:03
 */
public class Manager extends Leader {

    public Manager() {
        super(10000);
    }

    @Override
    protected void reply(ProgramApes ape) {
        System.out.println(ape.getApply());
        System.out.println("Manager: Of course Yes!");
    }
}

