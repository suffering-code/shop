package com.qf.consumer;
import  com.qf.utils.rabbitmqutils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
    public static void main(String[] args) throws IOException {
        Connection connection = rabbitmqutils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hellobamf",false,false,false,null);
        channel.basicConsume("hellobamf",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费:"+new String(body,"utf-8"));
            }
        });
    }
}
