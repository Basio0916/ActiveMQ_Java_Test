package com.mishibashi.activemqtest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerMain {

	public static void main(String[] args) throws JMSException {
        // 1. ActiveMQConnectionFactory を作成する
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

        // 2. Connection を作成する
        Connection connection = connectionFactory.createConnection();

        // 3. Session を作成する
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4. 受信する Destination を作成する
        Destination destination = session.createTopic("test-topic");

        // 5. Consumer を作成する
        MessageConsumer consumer = session.createConsumer(destination);

        // 6. MessageListener を実装したクラスを作成する
        MessageListener listener = new MyMessageListener();

        // 7. MessageListener を Consumer に登録する
        consumer.setMessageListener(listener);

        // 8. Connection を開始する
        connection.start();

        // 9. メッセージの受信を継続するために ExecutorService を作成する
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 10. ExecutorService を使用して Consumer を実行する
        executorService.execute(() -> {
            try {
                Thread.sleep(10000); // 10 秒間待機する
                connection.close(); // Connection を閉じる
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}
	
    static class MyMessageListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            try {
                System.out.println("Received message: " + ((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }

}
