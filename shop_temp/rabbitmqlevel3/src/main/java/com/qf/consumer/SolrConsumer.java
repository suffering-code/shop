package com.qf.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component // <bean>
public class SolrConsumer {

    @RabbitListener(queues = "solr_queue")
    public void add(String msg){
        System.out.println("SolrConsumer.addSolrCore :"+msg);
    }
}
