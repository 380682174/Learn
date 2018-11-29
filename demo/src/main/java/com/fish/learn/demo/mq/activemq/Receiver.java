package com.fish.learn.demo.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/10/2 16:54
 */
public class Receiver {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("username","password","tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("queue1");

        MessageConsumer messageConsumer = session.createConsumer(destination);

        while (true){
            TextMessage msg = (TextMessage) messageConsumer.receive();
            if(msg == null){
                break;
            }
            System.out.println("收到消息："+msg.getText());
        }

        if(connection != null){
            connection.close();
        }
    }
}
