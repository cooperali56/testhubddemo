package com.testhub.backend.service;

import com.testhub.backend.dto.LoginRequest;
import com.testhub.backend.dto.LoginResponse;
import com.testhub.backend.dto.RegisterRequest;
import com.testhub.backend.dto.UserDTO;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求
     * @return 登录响应
     */
    LoginResponse login(LoginRequest loginRequest);
    
    /**
     * 用户注册
     *
     * @param registerRequest 注册请求
     * @return 注册用户信息
     */
    UserDTO register(RegisterRequest registerRequest);
} 