package com.qf.spb1905.shop_sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ShopSsoApplicationTests {

    @Test
    public void contextLoads() {

        String c = null;
        if(StringUtils.isEmpty(c)){
            System.out.println("ShopSsoApplicationTests.contextLoads");
        }
    }

}
