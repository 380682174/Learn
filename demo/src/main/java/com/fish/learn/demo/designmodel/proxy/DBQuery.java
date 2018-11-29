package com.fish.learn.demo.designmodel.proxy;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/9/8 13:24
 */
public class DBQuery implements IDBQuery {

    public DBQuery(){
        try {
            //耗时操作
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String request() {
        return "request String";
    }
}
