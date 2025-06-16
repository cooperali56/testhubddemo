package com.testhub.backend.controller;

import com.testhub.backend.dto.UserDTO;
import com.testhub.backend.service.UserService;
import com.testhub.backend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户控制器
 */
@Tag(name = "用户接口", description = "用户管理相关接口")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
public class UserController {

    private final UserService userService;

    /**
     * 获取所有用户
     */
    @Operation(summary = "获取所有用户", description = "获取系统中的所有用户信息")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<UserDTO>> findAll() {
        return Result.success(userService.findAll());
    }

    /**
     * 根据ID获取用户
     */
    @Operation(summary = "获取用户详情", description = "根据ID获取用户详细信息")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.user.id == #id")
    public Result<UserDTO> findById(
            @Parameter(description = "用户ID", required = true) 
            @PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        return userDTO != null ? Result.success(userDTO) : Result.notFound("用户不存在");
    }

    /**
     * 创建用户
     */
    @Operation(summary = "创建用户", description = "创建新的用户")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<UserDTO> create(
            @Parameter(description = "用户信息", required = true) 
            @Valid @RequestBody UserDTO userDTO) {
        return Result.success(userService.create(userDTO));
    }

    /**
     * 更新用户
     */
    @Operation(summary = "更新用户", description = "更新已有的用户信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.user.id == #id")
    public Result<UserDTO> update(
            @Parameter(description = "用户ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "用户信息", required = true) 
            @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.update(id, userDTO);
        return updatedUser != null ? Result.success(updatedUser) : Result.notFound("用户不存在");
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户", description = "删除指定的用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> delete(
            @Parameter(description = "用户ID", required = true) 
            @PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }

    /**
     * 获取当前登录用户信息
     */
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的详细信息")
    @GetMapping("/current")
    public Result<UserDTO> getCurrentUser() {
        UserDTO currentUser = userService.getCurrentUserDTO();
        return currentUser != null ? Result.success(currentUser) : Result.unauthorized("未登录");
    }
    
    /**
     * 修改用户状态
     */
    @Operation(summary = "修改用户状态", description = "启用或禁用用户")
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<UserDTO> updateStatus(
            @Parameter(description = "用户ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "用户状态：1-启用，0-禁用", required = true) 
            @PathVariable Integer status) {
        UserDTO userDTO = userService.updateStatus(id, status);
        return userDTO != null ? Result.success(userDTO) : Result.notFound("用户不存在");
    }
} 