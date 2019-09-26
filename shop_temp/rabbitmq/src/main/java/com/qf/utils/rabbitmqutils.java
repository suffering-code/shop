package com.qf.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class rabbitmqutils {

    private static  final String username ="guest";
    private static  final String password="guest";
    private static  final String host="192.168.91.111";
    private static  final Integer port=5672;
    private static  final String vhost="/";

    private static ConnectionFactory connectionFactory =null;

    static {
        connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);
            connectionFactory.setHost(host);
            connectionFactory.setPort(port);
            connectionFactory.setVirtualHost(vhost);
    }

    public static Connection getConnection(){
        try {
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
