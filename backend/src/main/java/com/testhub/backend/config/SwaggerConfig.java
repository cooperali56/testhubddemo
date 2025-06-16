package com.testhub.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档配置
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TestHub API文档")
                        .description("TestHub测试管理平台接口文档")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("TestHub Team")
                                .email("admin@testhub.com")
                                .url("https://www.testhub.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                // 添加JWT认证配置
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }

    /**
     * 认证相关接口分组
     */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("认证接口")
                .pathsToMatch("/auth/**")
                .build();
    }

    /**
     * 用户相关接口分组
     */
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户接口")
                .pathsToMatch("/api/user/**")
                .build();
    }

    /**
     * 测试用例相关接口分组
     */
    @Bean
    public GroupedOpenApi testCaseApi() {
        return GroupedOpenApi.builder()
                .group("测试用例接口")
                .pathsToMatch("/api/testcase/**")
                .build();
    }

    /**
     * 测试报告相关接口分组
     */
    @Bean
    public GroupedOpenApi reportApi() {
        return GroupedOpenApi.builder()
                .group("测试报告接口")
                .pathsToMatch("/api/report/**")
                .build();
    }
} 