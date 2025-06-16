package com.testhub.backend.service;

import com.testhub.backend.dto.UserDTO;
import com.testhub.backend.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(String username);

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    List<UserDTO> findAll();

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    UserDTO findById(Long id);

    /**
     * 创建用户
     *
     * @param userDTO 用户信息
     * @return 创建的用户
     */
    UserDTO create(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param id 用户ID
     * @param userDTO 用户信息
     * @return 更新后的用户
     */
    UserDTO update(Long id, UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void delete(Long id);

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    User getCurrentUser();

    /**
     * 获取当前登录用户DTO
     * 
     * @return 当前登录用户DTO
     */
    UserDTO getCurrentUserDTO();
    
    /**
     * 更新用户状态
     *
     * @param id 用户ID
     * @param status 状态（1-启用，0-禁用）
     * @return 更新后的用户
     */
    UserDTO updateStatus(Long id, Integer status);
} 