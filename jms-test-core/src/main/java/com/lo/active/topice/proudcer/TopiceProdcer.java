package com.lo.active.topice.proudcer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopiceProdcer {

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                                        ActiveMQConnection.DEFAULT_PASSWORD,
                                        ActiveMQConnection.DEFAULT_BROKER_URL);
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //消息的目的址，发送给谁    创建一个主题
        Destination destination = session.createTopic("topice-test");
        //创建消息的生产者
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 100; i++) {
            //创建消息内容
            TextMessage textMessage = session.createTextMessage("主题消息：" + i);
            //发送消息
            producer.send(textMessage);
            System.out.println("主题消息：" + i);
        }

        //发送消息的一方需要把连接关闭
        connection.close();
    }
}
