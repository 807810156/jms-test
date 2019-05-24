package com.lo.active.queues.consumers;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueuesConsumers {

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //消息的目的地;消息来源那里   创建一个队列
        Destination destination = session.createQueue("queues-test");
        //创建消息消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //创建一个监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("接收到的消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
