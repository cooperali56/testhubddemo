package com.testhub.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springdoc.core.SpringDocConfiguration;

/**
 * TestHub应用启动类
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.testhub.backend"})
public class TestHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestHubApplication.class, args);
        System.out.println("TestHub应用启动成功！");
        System.out.println("API文档地址: http://localhost:8080/doc.html");
    }
} 