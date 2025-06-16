package com.testhub.backend.enums;

import lombok.Getter;

/**
 * 测试用例状态枚举
 */
@Getter
public enum TestCaseStatusEnum {

    /**
     * 未开始
     */
    NOT_STARTED(0, "未开始"),

    /**
     * 进行中
     */
    IN_PROGRESS(1, "进行中"),

    /**
     * 已完成
     */
    COMPLETED(2, "已完成"),

    /**
     * 已失败
     */
    FAILED(3, "已失败"),

    /**
     * 已跳过
     */
    SKIPPED(4, "已跳过"),

    /**
     * 已禁用
     */
    DISABLED(5, "已禁用");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;
    
    TestCaseStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
} 