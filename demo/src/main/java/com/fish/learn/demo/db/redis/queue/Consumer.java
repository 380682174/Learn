package com.fish.learn.demo.db.redis.queue;

import com.fish.learn.demo.db.redis.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/6/27 18:00
 */
public class Consumer extends Thread {
    String name;

    public Consumer(String name) {
        this.name = name;
    }

    @Override
    public void run(){
        Jedis jedis;
        while(true) {
            jedis = RedisUtil.getJedis();
            jedis.select(4);//选择数据库  默认0
            //阻塞式brpop，List中无数据时阻塞
            //参数0表示一直阻塞下去，直到List出现数据
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> list = jedis.brpop(0, "mylist");//在key对应list尾部获取元素
            for(String s : list) {
                System.out.println(name+"   "+s);
            }
            jedis.close();

        }
    }
}
