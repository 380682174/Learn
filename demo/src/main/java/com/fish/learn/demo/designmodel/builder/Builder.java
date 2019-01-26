package com.fish.learn.demo.designmodel.builder;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/26 13:43
 */
public abstract class Builder {

    /**
     * 第一步：装CPU
     * 声明为抽象方法，具体由子类实现
     */
    public abstract void  buildCPU();

    /**
     * 第二步：装主板
     * 声明为抽象方法，具体由子类实现
     */
    public abstract void buildMainboard();

    /**
     * 第三步：装硬盘
     * 声明为抽象方法，具体由子类实现
     */
    public abstract void buildHD();

    /**
     * 返回产品的方法：获得组装好的电脑
     * @return
     */
    public abstract Computer getComputer();
}
