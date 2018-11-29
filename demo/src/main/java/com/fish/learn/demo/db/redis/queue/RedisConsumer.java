package com.fish.learn.demo.db.redis.queue;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/6/27 18:01
 */
public class RedisConsumer {
    public static void main(String[] args) {
        Consumer mq1=new Consumer("mq1");
        mq1.start();
    }
}
