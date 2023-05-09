package com.mishibashi.activemqtest;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
public class PublisherMain {

	public static void main(String[] args) throws JMSException {
        // 1. ActiveMQConnectionFactory を作成する
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

        // 2. Connection を作成する
        Connection connection = connectionFactory.createConnection();

        // 3. Session を作成する
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4. 送信する Destination を作成する
        Destination destination = session.createTopic("test-topic");

        // 5. MessageProducer を作成する
        MessageProducer producer = session.createProducer(destination);

        // 6. メッセージを作成する
        TextMessage message = session.createTextMessage("Hello, world!");

        // 7. MessageProducer を使用してメッセージを送信する
        producer.send(message);

        // 8. Connection を閉じる
        connection.close();
	}

}
