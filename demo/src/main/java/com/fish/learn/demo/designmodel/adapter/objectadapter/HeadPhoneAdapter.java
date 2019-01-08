package com.fish.learn.demo.designmodel.adapter.objectadapter;

import com.fish.learn.demo.designmodel.adapter.classadapter.HeadPhone;
import com.fish.learn.demo.designmodel.adapter.classadapter.Iphone7;

/**
 * @Description: lightning to 3.5mm耳机 转换器 [Adapter - 适配器]
 * @Author devin.jiang
 * @CreateDate 2019/1/8 9:57
 */
public class HeadPhoneAdapter implements Iphone7 {

    private HeadPhone headPhone;

    HeadPhoneAdapter(HeadPhone headPhone){
        this.headPhone = headPhone;
    }

    @Override
    public void listenWithLightning() {

    }

    public void listenWithCommon(){
        headPhone.listenWithCommon();
    }
}
