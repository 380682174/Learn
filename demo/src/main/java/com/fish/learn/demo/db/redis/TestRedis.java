package com.fish.learn.demo.db.redis;

import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/6/27 17:43
 */
@Log4j
public class TestRedis {
    private Jedis jedis;

    @Before
    public void setup() {
        //连接redis服务器，192.168.0.100:6379
        /*jedis = new Jedis("192.168.0.100", 6379);
        //权限认证
        jedis.auth("admin");*/
        jedis = RedisUtil.getJedis();
    }

    /**
     * redis存储字符串
     */
    @Test
    public void testString() {
        //-----添加数据----------
        jedis.set("name","xinxin");//向key-->name中放入了value-->xinxin
        System.out.println(jedis.get("name"));//执行结果：xinxin

        jedis.append("name", " is my lover"); //拼接
        System.out.println(jedis.get("name"));

        jedis.del("name");  //删除某个键
        System.out.println(jedis.get("name"));
        //设置多个键值对
        jedis.mset("name","liuling","age","23","qq","476777XXX");
        jedis.incr("age"); //进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

    }

    /**
     * redis操作Map
     */
    @Test
    public void testMap() {
        //-----添加数据----------
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        jedis.hmset("user",map);
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);

        //删除map中的某个键值
        jedis.hdel("user","age");
        System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value

        Iterator<String> iter=jedis.hkeys("user").iterator();
        while (iter.hasNext()){
            String key = iter.next();
            System.out.println(key+":"+jedis.hmget("user",key));
        }
    }

    /**
     * jedis操作List
     */
    @Test
    public void testList(){
        //开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework",0,-1));
        //先向key java framework中存放三条数据
        jedis.lpush("java framework","spring");
        jedis.lpush("java framework","struts");
        jedis.lpush("java framework","hibernate");
        //再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework",0,-1));

        jedis.del("java framework");
        jedis.rpush("java framework","spring");
        jedis.rpush("java framework","struts");
        jedis.rpush("java framework","hibernate");
        System.out.println(jedis.lrange("java framework",0,-1));
    }

    /**
     * jedis操作Set
     */
    @Test
    public void testSet(){

        jedis.sadd("dataList","\"0_14_20_7476\"");
        jedis.sadd("dataList","\"0_14_20_7475\"");
        jedis.sadd("dataList","\"0_14_20_7474\"");
        jedis.sadd("dataList","\"0_14_20_7473\"");
        jedis.sadd("dataList","\"0_14_20_7472\"");
        jedis.sadd("dataList","\"0_14_20_7471\"");
        jedis.sadd("dataList","\"0_14_20_7470\"");
        jedis.sadd("dataList","\"0_14_20_7469\"");
        jedis.sadd("dataList","\"0_14_20_7468\"");
        jedis.sadd("dataList","\"0_14_20_7467\"");

        jedis.sadd("dataList","\"0_14_20_7466\"");
        jedis.sadd("dataList","\"0_13_22_7454\"");
        jedis.sadd("dataList","\"0_13_22_7453\"");
        jedis.sadd("dataList","\"0_13_22_7452\"");
        jedis.sadd("dataList","\"0_13_22_7451\"");
        jedis.sadd("dataList","\"0_13_22_7450\"");
        jedis.sadd("dataList","\"0_13_22_7449\"");
        jedis.sadd("dataList","\"0_13_22_7448\"");
        jedis.sadd("dataList","\"0_13_22_7447\"");
        jedis.sadd("dataList","\"0_13_22_7446\"");

        jedis.sadd("dataList","\"0_13_22_7445\"");
        jedis.sadd("dataList","\"0_13_22_7444\"");
        jedis.sadd("dataList","\"0_13_22_7443\"");
        jedis.sadd("dataList","\"0_13_22_7442\"");
        jedis.sadd("dataList","\"0_13_22_7441\"");
        jedis.sadd("dataList","\"0_13_22_7440\"");
        jedis.sadd("dataList","\"0_13_22_7439\"");
        jedis.sadd("dataList","\"0_13_22_7438\"");
        jedis.sadd("dataList","\"0_13_22_7437\"");
        jedis.sadd("dataList","\"0_13_22_7436\"");

        jedis.sadd("dataList","\"0_13_22_7435\"");
        jedis.sadd("dataList","\"0_13_22_7434\"");
        jedis.sadd("dataList","\"0_13_22_7433\"");
        jedis.sadd("dataList","\"0_13_22_7432\"");
        jedis.sadd("dataList","\"0_13_22_7431\"");
        jedis.sadd("dataList","\"0_13_22_7430\"");
        jedis.sadd("dataList","\"0_13_22_7429\"");
        jedis.sadd("dataList","\"0_13_22_7428\"");
        jedis.sadd("dataList","\"0_13_22_7427\"");
        jedis.sadd("dataList","\"0_13_22_7426\"");

        jedis.sadd("dataList","\"0_13_22_7425\"");
        jedis.sadd("dataList","\"0_13_22_7424\"");
        jedis.sadd("dataList","\"0_13_22_7423\"");
        jedis.sadd("dataList","\"0_13_22_7422\"");
        jedis.sadd("dataList","\"0_13_22_7421\"");
        jedis.sadd("dataList","\"0_13_22_7420\"");
        jedis.sadd("dataList","\"0_13_22_7419\"");
        jedis.sadd("dataList","\"0_13_22_7418\"");
        jedis.sadd("dataList","\"0_13_22_7417\"");
        jedis.sadd("dataList","\"0_13_22_7416\"");

        jedis.sadd("dataList","\"0_13_22_7415\"");
        jedis.sadd("dataList","\"0_13_22_7414\"");
        jedis.sadd("dataList","\"0_13_22_7413\"");
        jedis.sadd("dataList","\"0_13_22_7412\"");
        jedis.sadd("dataList","\"0_13_22_7411\"");
        jedis.sadd("dataList","\"0_13_22_7410\"");
        jedis.sadd("dataList","\"0_13_22_7409\"");
        jedis.sadd("dataList","\"0_13_22_7408\"");
        jedis.sadd("dataList","\"0_13_22_7407\"");
        jedis.sadd("dataList","\"0_13_22_7406\"");

        jedis.sadd("dataList","\"0_13_22_7405\"");
        jedis.sadd("dataList","\"0_13_22_7404\"");
        jedis.sadd("dataList","\"0_13_22_7403\"");
        jedis.sadd("dataList","\"0_13_22_7402\"");
        jedis.sadd("dataList","\"0_13_22_7401\"");
        jedis.sadd("dataList","\"0_13_22_7400\"");
        jedis.sadd("dataList","\"0_13_22_7399\"");
        jedis.sadd("dataList","\"0_13_22_7398\"");
        jedis.sadd("dataList","\"0_13_22_7397\"");
        jedis.sadd("dataList","\"0_13_22_7396\"");

        jedis.sadd("dataList","\"0_13_22_7395\"");
        jedis.sadd("dataList","\"0_13_22_7394\"");
        jedis.sadd("dataList","\"0_13_22_7393\"");
        jedis.sadd("dataList","\"0_13_22_7392\"");
        jedis.sadd("dataList","\"0_13_22_7391\"");
        jedis.sadd("dataList","\"0_13_22_7390\"");
        jedis.sadd("dataList","\"0_13_22_7389\"");
        jedis.sadd("dataList","\"0_13_22_7388\"");
        jedis.sadd("dataList","\"0_13_22_7387\"");
        jedis.sadd("dataList","\"0_13_22_7386\"");

        jedis.sadd("dataList","\"0_13_22_7385\"");
        jedis.sadd("dataList","\"0_13_22_7384\"");
        jedis.sadd("dataList","\"0_13_22_7383\"");
        jedis.sadd("dataList","\"0_13_22_7382\"");
        jedis.sadd("dataList","\"0_13_22_7381\"");
        jedis.sadd("dataList","\"0_13_22_7380\"");
        jedis.sadd("dataList","\"0_13_22_7379\"");
        jedis.sadd("dataList","\"0_13_22_7378\"");
        jedis.sadd("dataList","\"0_13_22_7377\"");
        jedis.sadd("dataList","\"0_13_22_7376\"");

        jedis.sadd("dataList","\"0_13_22_7375\"");
        jedis.sadd("dataList","\"0_13_22_7374\"");
        jedis.sadd("dataList","\"0_13_22_7373\"");
        jedis.sadd("dataList","\"0_13_22_7372\"");
        jedis.sadd("dataList","\"0_13_22_7371\"");
        jedis.sadd("dataList","\"0_13_22_7370\"");
        jedis.sadd("dataList","\"0_13_22_7369\"");
        jedis.sadd("dataList","\"0_13_22_7368\"");
        jedis.sadd("dataList","\"0_13_22_7367\"");
        jedis.sadd("dataList","\"0_13_22_7366\"");


        /*//添加
        jedis.sadd("user","liuling");
        jedis.sadd("user","xinxin");
        jedis.sadd("user","ling");
        jedis.sadd("user","zhangxinxin");
        jedis.sadd("user","who");
        //移除noname
        jedis.srem("user","who");
        System.out.println(jedis.smembers("user"));//获取所有加入的value
        System.out.println(jedis.sismember("user", "who"));//判断 who 是否是user集合的元素
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));//返回集合的元素个数*/
    }

    @Test
    public void test() throws InterruptedException {
        //jedis 排序
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
        jedis.del("a");//先清除数据，再加入数据进行测试
        jedis.rpush("a", "1");
        jedis.lpush("a","6");
        jedis.lpush("a","3");
        jedis.lpush("a","9");
        System.out.println(jedis.lrange("a",0,-1));// [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果
        System.out.println(jedis.lrange("a",0,-1));
    }

    @Test
    public void testRedisPool() {
        RedisUtil.getJedis().set("newname", "中文测试");
        System.out.println(RedisUtil.getJedis().get("newname"));
    }
}