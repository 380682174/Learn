package com.fish.learn.demo.designmodel.responsibilitychain;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 16:55
 */
public abstract class ProgramApes {

    /**
     * 获取程序员具体的差旅费用
     *
     * @return 要多少钱
     */
    public abstract int getExpenses();

    /**
     * 获取差旅费申请
     *
     * @return Just a request
     */
    public abstract String getApply();

}
