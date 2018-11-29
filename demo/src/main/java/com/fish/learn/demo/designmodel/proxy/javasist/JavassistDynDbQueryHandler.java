package com.fish.learn.demo.designmodel.proxy.javasist;

import com.fish.learn.demo.designmodel.proxy.DBQuery;
import com.fish.learn.demo.designmodel.proxy.IDBQuery;
import javassist.util.proxy.MethodHandler;
import java.lang.reflect.Method;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/9/8 14:47
 */
public class JavassistDynDbQueryHandler implements MethodHandler {

    IDBQuery real = null;

    @Override
    public Object invoke(Object o, Method method, Method method1, Object[] objects) throws Throwable {
        if(real == null){
            real = new DBQuery();
        }

        return real.request();
    }

}
