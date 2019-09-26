package com.qf.spb1905.usersys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.qf")
@MapperScan(value = "com.qf.dao")
@EnableCaching
public class UsersysApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersysApplication.class, args);
    }

}
