package com.qf.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hcontroller {
    @Autowired
    private RabbitTemplate template;
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String  hello(String  msg){
        System.out.println("HelloContorller.hello"+msg);
        template.convertAndSend("goods_exchange","",msg);
        return "ok";
    }

}
