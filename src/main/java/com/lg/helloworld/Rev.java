package com.lg.helloworld;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class Rev {
	 private final static String QUEUE_NAME = "hello";
	 
	 public static void main(String[] args) {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("host");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("username");
	    connectionFactory.setPassword("password");
	    connectionFactory.setVirtualHost("/");
	    connectionFactory.setConnectionTimeout(5000);
		Connection connection = null;
	final	Channel channel ;
	    try {
	    	connection	= connectionFactory.newConnection();
	    	 channel = connection.createChannel();
	       channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	       System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	
	       Consumer consumer = new DefaultConsumer(channel) {
	    	   public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
	    		   String message = new String(body,"UTF-8");
	    		   System.out.println(message);
	    		   channel.basicAck(envelope.getDeliveryTag(), false);
	    		   channel.basicReject(envelope.getDeliveryTag(), false);
	    	   };
	       };
	       channel.basicConsume(QUEUE_NAME, true, consumer);
	    } catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
