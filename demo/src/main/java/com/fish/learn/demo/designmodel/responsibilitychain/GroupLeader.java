package com.fish.learn.demo.designmodel.responsibilitychain;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 17:00
 */
public class GroupLeader extends Leader{

    public GroupLeader() {
        super(1000);
    }

    @Override
    protected void reply(ProgramApes ape) {
        System.out.println(ape.getApply());
        System.out.println("GroupLeader: Of course Yes!");
    }
}
