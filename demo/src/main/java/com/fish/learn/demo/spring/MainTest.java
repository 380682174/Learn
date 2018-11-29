package com.fish.learn.demo.spring;

import com.spring.springevent.MessageEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/7/10 14:57
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MessageEvent messageEvent = new MessageEvent("","这是一条消息事件发出的消息内容");
        context.publishEvent(messageEvent);
    }
}
