package com.fish.learn.demo.designmodel.responsibilitychain;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 16:58
 */
public interface IPower {
    /**
     * 处理请求
     *
     * @param ape
     *            具体的猿
     */
    public void handleRequest(ProgramApes ape);
}
