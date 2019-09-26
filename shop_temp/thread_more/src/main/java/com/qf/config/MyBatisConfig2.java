package com.qf.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyBatisConfig2 {

    @Value("${spring.datasource1.url}")
    private String url;

    @Value("${spring.datasource2.url}")
    private String url2;

    @Value("${spring.datasource1.username}")
    private String username;

    @Value("${spring.datasource1.password}")
    private String password;

    @Value("${spring.datasource1.driver-class-name}")
    private String driverClass;


    @Value("${mybatis.mapper-locations}")
    private String locations;

    /**
     * 数据源1
     * @return
     */
    @Bean
    public HikariDataSource dataSource1(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setDriverClassName(driverClass);
        dataSource.setJdbcUrl(url);
        return dataSource;
    }


    /**
     * 数据源2
     * @return
     */
    @Bean
    public HikariDataSource dataSource2(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setDriverClassName(driverClass);
        dataSource.setJdbcUrl(url2);
        return dataSource;
    }

    /**
     * 数据源3(包含了1和2)
     * @param dataSource1
     * @param dataSource2
     * @return
     */
    @Bean
    public DataSource dynamic(DataSource dataSource1,DataSource dataSource2){

        //  把两个数据源放入到map中
        Map<Object,Object> map = new HashMap<>();
        map.put("d1",dataSource1);
        map.put("d2",dataSource2);


        // 1.创建一个动态数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setDefaultTargetDataSource(dataSource1); // 默认的一个数据源
        dataSource.setTargetDataSources(map); // 把两个数据源放入到Map中给DynamicDataSource
        return dataSource;

    }


    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dynamic){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamic);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.qf.entity");
        try {
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(locations));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }
}
