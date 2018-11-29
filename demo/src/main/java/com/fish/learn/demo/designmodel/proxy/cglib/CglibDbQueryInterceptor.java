package com.fish.learn.demo.designmodel.proxy.cglib;

import com.fish.learn.demo.designmodel.proxy.DBQuery;
import com.fish.learn.demo.designmodel.proxy.IDBQuery;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/9/8 14:38
 */
public class CglibDbQueryInterceptor implements MethodInterceptor {

    private IDBQuery real = null;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        if(real == null){
            real = new DBQuery();
        }

        return real.request();
    }
}
