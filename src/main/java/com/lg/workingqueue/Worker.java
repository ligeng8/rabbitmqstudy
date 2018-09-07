package com.lg.workingqueue;

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
public class Worker {
	private static final String TASK_QUEUE_NAME = "task_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.50.123");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("username");
		connectionFactory.setPassword("password");
		connectionFactory.setVirtualHost("/v");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		channel.basicQos(1); // accept only one unack-ed message at a time (see below)
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				// TODO Auto-generated method stub
				try {
					String task = new String(body, "UTF-8");
					doWork( task);
					this.getChannel().basicAck(envelope.getDeliveryTag(), false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					this.getChannel().basicReject(envelope.getDeliveryTag(), true);
				}
			}
		};
		
		channel.basicConsume(TASK_QUEUE_NAME, false, defaultConsumer);
	}
	
	
	private static void doWork(String task) throws InterruptedException {
	    for (char ch: task.toCharArray()) {
	        if (ch == '.') Thread.sleep(1000);
	    }
	}
}
