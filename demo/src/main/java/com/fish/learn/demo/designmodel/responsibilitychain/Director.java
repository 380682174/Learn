package com.fish.learn.demo.designmodel.responsibilitychain;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 17:02
 */
public class Director extends Leader {

    public Director() {
        super(5000);
    }

    @Override
    protected void reply(ProgramApes ape) {
        System.out.println(ape.getApply());
        System.out.println("Director: Of course Yes!");
    }
}
