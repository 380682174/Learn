package com.fish.learn.demo.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/10/2 16:29
 */
public class Sender {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("username","password","tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("queue1");

        MessageProducer messageProducer = session.createProducer(destination);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for(int i = 0;i<5;i++){
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("我是消息内容,id为"+i);

            messageProducer.send(textMessage);
            System.out.println("发送消息："+textMessage.getText());
        }
        if(connection != null){
            connection.close();
        }

    }

}
