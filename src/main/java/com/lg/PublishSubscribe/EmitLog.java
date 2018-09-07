package com.lg.PublishSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * 
 * @author ligeng3
 *
 */
public class EmitLog {

	
	private static final String EXCAHNGE_NAME = "logs";
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("host");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("");
		connectionFactory.setPassword("password");
		connectionFactory.setVirtualHost("/host");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCAHNGE_NAME, "fanout");
		   String  message= getMessage(args);
		channel.basicPublish(EXCAHNGE_NAME, "", null, message.getBytes());
		channel.close();
		connection.close();
		
	}
	
	 private static String getMessage(String[] strings){
		    if (strings.length < 1)
		    	    return "info: Hello World!";
		    return joinStrings(strings, " ");
		  }

		  private static String joinStrings(String[] strings, String delimiter) {
		    int length = strings.length;
		    if (length == 0) return "";
		    StringBuilder words = new StringBuilder(strings[0]);
		    for (int i = 1; i < length; i++) {
		        words.append(delimiter).append(strings[i]);
		    }
		    return words.toString();
		  }
}
