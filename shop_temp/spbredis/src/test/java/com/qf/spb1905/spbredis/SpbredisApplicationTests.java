package com.qf.spb1905.spbredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpbredisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void contextLoads() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","damin");
        Object name = valueOperations.get("name");
        System.out.println(name);
    }
    @Test
    public void testDateType(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","zhouqi");
        System.out.println(valueOperations.get("name"));

        System.out.println("-------------------------------------------------");
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("user","age",12);
        hashOperations.put("user","sex","woman");
        System.out.println(hashOperations.get("user","sex"));
        System.out.println("----------------------------------------------");

        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPushAll("books","java","js","jsp");
        List books = listOperations.range("books", 0, -1);
        System.out.println(books);
        System.out.println("---------------------------------------------");

        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("phones","huawei","xiaomi","phoenx");
        Set phones = setOperations.members("phones");
        System.out.println(phones);

        System.out.println("---------------------------------------------");

        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("score","zs",22.0);
        zSetOperations.add("score","ls",20.0);
        zSetOperations.add("score","ww",15.0);

        Set score = zSetOperations.range("score", 0, -1);
        System.out.println(score);

    }


}
