package com.fish.learn.demo.designmodel.proxy.jdk;

import com.fish.learn.demo.designmodel.proxy.DBQuery;
import com.fish.learn.demo.designmodel.proxy.IDBQuery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/9/8 14:30
 */
public class JdkDbQueryHandler implements InvocationHandler {

    //主题接口
    IDBQuery real = null;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(real == null){
            //如果是第一次调用，则生成真实对象
            real = new DBQuery();
        }

        //使用真实主题完成实际操作
        return real.request();
    }
}
