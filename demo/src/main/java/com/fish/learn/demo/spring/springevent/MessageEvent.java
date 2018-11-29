package com.fish.learn.demo.spring.springevent;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/7/10 14:50
 */
@Data
public class MessageEvent extends ApplicationEvent {

    private String messageContent;

    public MessageEvent(Object source) {
        super(source);
    }

    public MessageEvent(Object source,String messageContent) {
        super(source);
        this.messageContent = messageContent;
    }
}
