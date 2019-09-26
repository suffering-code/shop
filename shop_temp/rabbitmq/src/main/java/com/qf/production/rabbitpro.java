package com.qf.production;

import  com.qf.utils.rabbitmqutils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class rabbitpro {
    public static void main(String[] args) throws IOException {
        Connection connection = rabbitmqutils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare("hellobamf",false,false,false,null);
        String msg = "Hello RabbitMQ Java 123 a b";
        channel.basicPublish("","hellobamf",null,msg.getBytes());
        connection.close();
        System.out.println("发送完毕");


    }
}
