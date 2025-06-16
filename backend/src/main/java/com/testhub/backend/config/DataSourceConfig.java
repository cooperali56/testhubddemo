package com.testhub.backend.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername("testhub");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(20);
        dataSource.setIdleTimeout(30000);
        dataSource.setConnectionTimeout(30000);
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.addDataSourceProperty("allowPublicKeyRetrieval", "true");
        dataSource.addDataSourceProperty("useSSL", "false");
        dataSource.addDataSourceProperty("serverTimezone", "Asia/Shanghai");
        dataSource.addDataSourceProperty("characterEncoding", "utf-8");
        dataSource.addDataSourceProperty("useUnicode", "true");
        return dataSource;
    }
} 