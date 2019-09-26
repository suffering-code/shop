package com.qf.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ItemConsumer {

        @RabbitListener(queues = "item_queue")
    public void add(String msg){
            System.out.println("ItemConsumer.add"+msg);
        }
}
