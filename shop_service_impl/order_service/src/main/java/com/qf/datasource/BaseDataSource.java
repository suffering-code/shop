package com.qf.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

import javax.sql.DataSource;

@Data
public class BaseDataSource {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String aliases;

    public DataSource getDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        return dataSource;
    }
}
