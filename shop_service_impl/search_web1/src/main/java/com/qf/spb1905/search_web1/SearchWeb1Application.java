package com.qf.spb1905.search_web1;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.qf.listener",exclude = DataSourceAutoConfiguration.class)
@DubboComponentScan(basePackages = "com.qf.service.impl")
public class SearchWeb1Application {

    public static void main(String[] args) {
        SpringApplication.run(SearchWeb1Application.class, args);
    }

}
