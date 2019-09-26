package com.qf.spb1905.shop_front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ShopFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopFrontApplication.class, args);
    }

}
