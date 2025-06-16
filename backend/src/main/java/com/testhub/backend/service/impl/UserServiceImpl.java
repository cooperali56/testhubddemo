package com.testhub.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.testhub.backend.dto.UserDTO;
import com.testhub.backend.entity.User;
import com.testhub.backend.repository.UserRepository;
import com.testhub.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.selectList(null)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.selectById(id);
        return user != null ? convertToDTO(user) : null;
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 设置默认值
        user.setStatus(1);
        user.setDeleted(0);
        
        userRepository.insert(user);
        return convertToDTO(user);
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        User existingUser = userRepository.selectById(id);
        if (existingUser == null) {
            return null;
        }
        
        BeanUtils.copyProperties(userDTO, existingUser);
        existingUser.setId(id); // 确保ID不变
        
        // 如果修改了密码，则重新加密
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        
        userRepository.updateById(existingUser);
        return convertToDTO(existingUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            return findByUsername(username);
        }
        return null;
    }

    @Override
    public UserDTO getCurrentUserDTO() {
        User currentUser = getCurrentUser();
        return currentUser != null ? convertToDTO(currentUser) : null;
    }
    
    @Override
    @Transactional
    public UserDTO updateStatus(Long id, Integer status) {
        // 验证状态值
        if (status != 0 && status != 1) {
            throw new IllegalArgumentException("状态值不合法，必须是0或1");
        }
        
        User user = userRepository.selectById(id);
        if (user == null) {
            return null;
        }
        
        // 更新状态
        user.setStatus(status);
        userRepository.updateById(user);
        
        return convertToDTO(user);
    }

    /**
     * 将实体转换为DTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        // 不传输密码
        userDTO.setPassword(null);
        return userDTO;
    }
} 