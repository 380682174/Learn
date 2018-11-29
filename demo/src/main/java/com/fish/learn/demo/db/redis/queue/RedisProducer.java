package com.fish.learn.demo.db.redis.queue;

import com.redis.RedisUtil;
import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/6/27 16:49
 */
@Log4j
public class RedisProducer {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis=RedisUtil.getJedis();
        jedis.select(4);
        int count=0;
        while(count<100){
            log.info(count);
            Thread.sleep(200);
            jedis.lpush("mylist",String.valueOf(count));//lpush在key对应的list的头部添加字符串元素
            count++;
        }
        jedis.close();
    }
}
