package com.winter.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/***
 @author shenhy
 @create 2021-04-09 16:22 
 ***/
@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
@PropertySource("classpath:application.yml")
public class DruidConfiguration {

    @Value("${name}")
    private String name;
    @Value("${url}")
    private String url;
    @Value("${dbusername}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${type}")
    private String type;
    @Value("${driver-class-name}")
    private String driverClassName;
    @Value("${filters}")
    private String filters;
    @Value("${maxActive}")
    private int maxActive;
    @Value("${initialSize}")
    private int initialSize;
    @Value("${maxWait}")
    private int maxWait;
    @Value("${minIdle}")
    private int minIdle;
    @Value("${timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${validationQuery}")
    private String validationQuery;
    @Value("${testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${testOnReturn}")
    private boolean testOnReturn;
    @Value("${poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${maxOpenPreparedStatements}")
    private int maxOpenPreparedStatements;

    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxWait(maxWait);
        dataSource.setMinIdle(minIdle);
        dataSource.setTimeBetweenConnectErrorMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
//        dataSource.setFilters("mergeStat,wall,log4j");//mergeStat代替stat表示sql合并,wall表示防御SQL注入攻击
        return dataSource;
    }

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        try {
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory.getObject();
    }

}
