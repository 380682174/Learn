package com.fish.learn.demo.spring.springevent;

import com.spring.springevent.MessageEvent;
import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationListener;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/7/10 14:53
 */
@Log4j
public class MessageListener implements ApplicationListener<MessageEvent> {

    //监听到有事件发布了，则触发此事件处理
    @Override
    public void onApplicationEvent(MessageEvent messageEvent) {
        log.info("信息内容是："+messageEvent.getMessageContent());
        log.info("其他全部信息："+messageEvent.toString());
    }
}
