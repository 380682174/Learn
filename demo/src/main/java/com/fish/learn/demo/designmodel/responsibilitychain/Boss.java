package com.fish.learn.demo.designmodel.responsibilitychain;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 17:03
 */
public class Boss extends Leader {

    public Boss() {
        super(40000);
    }

    @Override
    protected void reply(ProgramApes ape) {
        System.out.println(ape.getApply());
        System.out.println("Boss: Of course Yes!");
    }
}

