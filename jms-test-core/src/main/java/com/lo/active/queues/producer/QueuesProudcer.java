package com.lo.active.queues.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueuesProudcer {

    private static final String user = ActiveMQConnectionFactory.DEFAULT_USER;
    private static final String password = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private static final String url = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {
        System.out.println(url);
        //创建连接工厂,工厂由ActiveMQ厂商提供
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //消息的目的地;消息发送给谁.   创建一个队列，名字随便起
        Destination destination = session.createQueue("queues-test");
        //创建消息生成者
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("test" + i);
            //发布消息
            producer.send(textMessage);
            System.out.println("发送消息成功: " + textMessage.getText());
        }

        //关闭连接
        connection.close();
    }
}
