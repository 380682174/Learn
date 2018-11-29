package com.fish.learn.demo.designmodel.future;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/6/19 19:29
 */
public class Client {
    public Data request (final String queryStr) {

        final FutureData future = new FutureData();


        /*new Thread(()->{
            RealData realData = new RealData(queryStr);
            future.setRealData(realData);
        }).start();*/

        return future;
    }
}
