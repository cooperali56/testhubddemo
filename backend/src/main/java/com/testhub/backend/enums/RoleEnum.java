package com.testhub.backend.enums;

import lombok.Getter;

/**
 * 角色枚举
 */
@Getter
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN("ADMIN", "管理员"),

    /**
     * 测试人员
     */
    TESTER("TESTER", "测试人员");

    /**
     * 角色编码
     */
    private final String code;

    /**
     * 角色名称
     */
    private final String name;
    
    RoleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
} 