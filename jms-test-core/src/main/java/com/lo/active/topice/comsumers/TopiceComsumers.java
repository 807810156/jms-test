package com.lo.active.topice.comsumers;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 注意：主题模式的消息，要先启动订阅者，再会接收到生产者生产的消息，订阅者无法接收生产者之前的消息
 */
public class TopiceComsumers {
    //默认用户
    private static final String user = ActiveMQConnection.DEFAULT_USER;
    //默认密码
    private static final String password = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认url
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {
        //创建连接工厂，让activeMQ提供
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建接收目标的地点， 接收主题
        Destination destination = session.createTopic("topice-test");
        //创建消息订阅者
        MessageConsumer consumer = session.createConsumer(destination);
        //创建消息监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    //监听到的消息
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("接收到的消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
