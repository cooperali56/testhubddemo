package com.testhub.backend.service.impl;

import com.testhub.backend.dto.LoginRequest;
import com.testhub.backend.dto.LoginResponse;
import com.testhub.backend.dto.RegisterRequest;
import com.testhub.backend.dto.UserDTO;
import com.testhub.backend.entity.User;
import com.testhub.backend.security.UserDetailsImpl;
import com.testhub.backend.service.AuthService;
import com.testhub.backend.service.UserService;
import com.testhub.backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现类
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authenticationToken = 
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            // 认证
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            
            // 保存认证信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 生成JWT
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);
            
            // 获取用户信息
            User user = userDetails.getUser();
            
            // 检查用户状态
            if (user.getStatus() == 0) {
                throw new org.springframework.security.authentication.DisabledException("账号已被禁用，请联系管理员");
            }
            
            // 构建响应
            return LoginResponse.builder()
                    .token(jwt)
                    .userId(user.getId())
                    .username(user.getUsername())
                    .name(user.getName())
                    .role(user.getRole())
                    .build();
        } catch (org.springframework.security.authentication.DisabledException e) {
            throw new RuntimeException("账号已被禁用，请联系管理员");
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new RuntimeException("用户名或密码错误");
        } catch (org.springframework.security.authentication.LockedException e) {
            throw new RuntimeException("账号已被锁定，请联系管理员");
        } catch (Exception e) {
            throw new RuntimeException("登录失败: " + e.getMessage());
        }
    }
    
    @Override
    public UserDTO register(RegisterRequest registerRequest) {
        // 检查用户名是否已存在
        User existingUser = userService.findByUsername(registerRequest.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建新用户
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(registerRequest.getUsername());
        userDTO.setPassword(registerRequest.getPassword());
        userDTO.setName(registerRequest.getName());
        userDTO.setEmail(registerRequest.getEmail());
        userDTO.setPhone(registerRequest.getPhone());
        userDTO.setRole("TESTER"); // 默认角色为测试人员
        userDTO.setStatus(1); // 默认状态为正常
        
        // 保存用户
        return userService.create(userDTO);
    }
} 