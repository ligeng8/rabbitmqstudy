package com.lg.PublishSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 
 * @author ligeng3
 *
 */
public class ReceiveLogs {
	private static final String EXCAHNGE_NAME = "logs";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("host");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("username");
		connectionFactory.setPassword("password");
		connectionFactory.setVirtualHost("/");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCAHNGE_NAME, "fanout");
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, EXCAHNGE_NAME, "");
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				// TODO Auto-generated method stub
				try {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message + "'");
					getChannel().basicAck(envelope.getDeliveryTag(), false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					getChannel().basicReject(envelope.getDeliveryTag(), true);
				}
			}
		};
		channel.basicConsume(queue, false, defaultConsumer);
	}
}
