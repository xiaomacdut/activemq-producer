package com.maj.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 发送消息到队列
 * @author xiaomacdut
 * @date 2019年3月1日 下午9:38:40
 */
public class JMSQueueProducer{
    
    public static void main(String [] args){
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.11.127:61616");
        Connection connection = null;
        try{
            
            connection = connectionFactory.createConnection();
            connection.start();
            
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 创建目的地
            Destination destination = session.createQueue("myQueue");
            // 创建发送者
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            for(int i = 0; i < 10; i++){
                // 创建需要发送的消息
                TextMessage message = session.createTextMessage("Hello World:" + i);
                // Text Map Bytes Stream Object
                producer.send(message);
            }
            
            // session.commit();
            session.close();
        }catch(JMSException e){
            e.printStackTrace();
        }finally{
            if(connection != null){
                try{
                    connection.close();
                }catch(JMSException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
