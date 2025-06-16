package com.testhub.backend.controller;

import com.testhub.backend.dto.LoginRequest;
import com.testhub.backend.dto.LoginResponse;
import com.testhub.backend.dto.RegisterRequest;
import com.testhub.backend.dto.UserDTO;
import com.testhub.backend.security.UserDetailsImpl;
import com.testhub.backend.service.AuthService;
import com.testhub.backend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 认证控制器
 */
@Tag(name = "认证接口", description = "用户登录相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "根据用户名和密码进行登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return Result.success("登录成功", loginResponse);
    }
    
    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "注册新用户")
    @PostMapping("/register")
    public Result<UserDTO> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO userDTO = authService.register(registerRequest);
        return Result.success("注册成功", userDTO);
    }

    /**
     * 获取当前用户信息
     */
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的信息")
    @PostMapping("/info")
    public Result<UserDetailsImpl> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Result.success(userDetails);
    }

    /**
     * 用户登出
     */
    @Operation(summary = "用户登出", description = "用户退出登录")
    @PostMapping("/logout")
    public Result<Void> logout() {
        SecurityContextHolder.clearContext();
        return Result.success();
    }
} 