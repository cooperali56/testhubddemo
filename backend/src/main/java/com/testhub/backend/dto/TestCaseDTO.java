package com.testhub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 测试用例DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用例ID
     */
    private Long id;

    /**
     * 用例名称
     */
    private String name;

    /**
     * 用例描述
     */
    private String description;

    /**
     * 前置条件
     */
    private String precondition;

    /**
     * 测试步骤
     */
    private String steps;

    /**
     * 预期结果
     */
    private String expectedResult;

    /**
     * 实际结果
     */
    private String actualResult;

    /**
     * 状态：0-未开始，1-进行中，2-已完成，3-已失败，4-已跳过，5-已禁用
     */
    private Integer status;

    /**
     * 优先级：0-低，1-中，2-高
     */
    private Integer priority;

    /**
     * 所属模块ID
     */
    private Long moduleId;

    /**
     * 所属项目ID
     */
    private Long projectId;

    /**
     * 创建者ID
     */
    private Long creatorId;

    /**
     * 创建者姓名
     */
    private String creatorName;

    /**
     * 执行者ID
     */
    private Long executorId;

    /**
     * 执行者姓名
     */
    private String executorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 