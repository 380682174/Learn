package com.fish.learn.demo.designmodel.adapter.interfaceadapter;

/**
 * @Description:当不希望实现一个接口中所有的方法时，可以创建一个抽象类继承该接口，
 * 并实现空方法或默认方法，子类只需继承该抽象类即可。
 * @Author devin.jiang
 * @CreateDate 2019/1/8 10:05
 */

public class HeadPhoneAdapter extends Adapter {
    @Override
    public void listenWithCommon() {

    }
}
