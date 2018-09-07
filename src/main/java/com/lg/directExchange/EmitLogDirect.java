package com.lg.directExchange;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class EmitLogDirect {
	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] args) throws IOException, TimeoutException {
      ConnectionFactory connectionFactory = new ConnectionFactory();
      connectionFactory.setHost("192.168.50.123");
      connectionFactory.setPort(5672);
      connectionFactory.setUsername("username");
      connectionFactory.setPassword("password");
      connectionFactory.setVirtualHost("/");
      Connection connection = connectionFactory.newConnection();
      Channel channel = connection.createChannel();
      channel.exchangeDeclare(EXCHANGE_NAME, "derict");
      String message = "";
      
      channel.basicPublish(EXCHANGE_NAME, Severity.INFO.name(), MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
      channel.close();
      connection.close();
      
      
	}
}
