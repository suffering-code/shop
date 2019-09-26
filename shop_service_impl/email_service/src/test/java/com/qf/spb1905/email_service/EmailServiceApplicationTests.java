package com.qf.spb1905.email_service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceApplicationTests {

    @Test
    public void contextLoads() {
        for (int i =0 ;i<10 ; i++){
            System.out.println((int)((Math.random()*9+1)*1000));
        }

    }

}
