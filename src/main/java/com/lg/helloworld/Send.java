package com.lg.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	
	private final static  Logger logger = LoggerFactory.getLogger(Send.class);
	  private final static String QUEUE_NAME = "hello";
	public static void main(String[] args) throws IOException, TimeoutException {
              ConnectionFactory connectionFactory = new ConnectionFactory();
              connectionFactory.setHost("192.168.50.123");
              connectionFactory.setPort(1000);
              connectionFactory.setUsername("username");
              connectionFactory.setPassword("password");
              connectionFactory.setVirtualHost("/");
              connectionFactory.setConnectionTimeout(5000);
              Connection connection = null;
              Channel channel =  null;
              try {
				  connection = connectionFactory.newConnection();
				  channel = connection.createChannel();
				  channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				  String message = "hello world";
				  channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info(e.getMessage());
			}finally {
				if(channel != null) {
					channel.close();
				}
				if(connection != null) {
					connection.close();
			}
		}
	}
}
