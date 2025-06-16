package com.testhub.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * JWT密钥
     */
    private String secret;

    /**
     * JWT过期时间（毫秒）
     */
    private Long expiration;

    /**
     * 请求头名称
     */
    private String header;

    /**
     * Token前缀
     */
    private String tokenPrefix;
} 