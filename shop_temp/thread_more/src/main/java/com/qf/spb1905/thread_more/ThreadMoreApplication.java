package com.qf.spb1905.thread_more;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.qf",exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.qf.dao")
public class ThreadMoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadMoreApplication.class, args);
    }

}
