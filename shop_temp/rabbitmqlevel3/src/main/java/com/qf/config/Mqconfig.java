package com.qf.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class Mqconfig {
    @Bean
    public Queue itemQueue(){
        System.out.println("Mqconfig.itemQueue");
        return  new Queue("item_queue") ;
    }
    @Bean
    public Queue solrQueue(){
        return new Queue("solr_queue");
    }
    @Bean
    public FanoutExchange goodsExchange(){
        return  new FanoutExchange("goods_exchange");
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(solrQueue()).to(goodsExchange());
    }
    @Bean
    public Binding binding2(){
        return  BindingBuilder.bind(itemQueue()).to(goodsExchange());
    }


}
