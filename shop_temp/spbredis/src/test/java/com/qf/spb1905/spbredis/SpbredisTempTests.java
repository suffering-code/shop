package com.qf.spb1905.spbredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpbredisTempTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testaa(){
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                try{
                    redisTemplate.multi();
                    redisTemplate.opsForValue().set("name","sb");

                    redisTemplate.opsForValue().set("admin","admin");

                    redisTemplate.exec();
                }catch (Exception e){
                    redisTemplate.discard();
                    e.printStackTrace();
                }
                return null;
            }
        });
    }


}
