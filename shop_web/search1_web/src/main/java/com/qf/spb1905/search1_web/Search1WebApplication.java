package com.qf.spb1905.search1_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.qf",exclude = DataSourceAutoConfiguration.class)
public class Search1WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(Search1WebApplication.class, args);
    }

}
