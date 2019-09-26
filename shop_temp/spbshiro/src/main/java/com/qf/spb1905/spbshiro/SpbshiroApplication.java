package com.qf.spb1905.spbshiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.qf")
public class SpbshiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpbshiroApplication.class, args);
    }

}
