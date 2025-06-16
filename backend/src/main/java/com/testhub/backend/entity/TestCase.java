package com.testhub.backend.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 测试用例实体
 */
@TableName("tb_test_case")
public class TestCase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
     * 执行者ID
     */
    private Long executorId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;

    public TestCase() {
    }

    public TestCase(Long id, String name, String description, String precondition, String steps, String expectedResult, String actualResult, Integer status, Integer priority, Long moduleId, Long projectId, Long creatorId, Long executorId, LocalDateTime createTime, LocalDateTime updateTime, Integer deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.precondition = precondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        this.status = status;
        this.priority = priority;
        this.moduleId = moduleId;
        this.projectId = projectId;
        this.creatorId = creatorId;
        this.executorId = executorId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrecondition() {
        return precondition;
    }

    public void setPrecondition(String precondition) {
        this.precondition = precondition;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    // Builder pattern
    public static TestCaseBuilder builder() {
        return new TestCaseBuilder();
    }

    public static class TestCaseBuilder {
        private Long id;
        private String name;
        private String description;
        private String precondition;
        private String steps;
        private String expectedResult;
        private String actualResult;
        private Integer status;
        private Integer priority;
        private Long moduleId;
        private Long projectId;
        private Long creatorId;
        private Long executorId;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private Integer deleted;

        public TestCaseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TestCaseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TestCaseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TestCaseBuilder precondition(String precondition) {
            this.precondition = precondition;
            return this;
        }

        public TestCaseBuilder steps(String steps) {
            this.steps = steps;
            return this;
        }

        public TestCaseBuilder expectedResult(String expectedResult) {
            this.expectedResult = expectedResult;
            return this;
        }

        public TestCaseBuilder actualResult(String actualResult) {
            this.actualResult = actualResult;
            return this;
        }

        public TestCaseBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public TestCaseBuilder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public TestCaseBuilder moduleId(Long moduleId) {
            this.moduleId = moduleId;
            return this;
        }

        public TestCaseBuilder projectId(Long projectId) {
            this.projectId = projectId;
            return this;
        }

        public TestCaseBuilder creatorId(Long creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public TestCaseBuilder executorId(Long executorId) {
            this.executorId = executorId;
            return this;
        }

        public TestCaseBuilder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public TestCaseBuilder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public TestCaseBuilder deleted(Integer deleted) {
            this.deleted = deleted;
            return this;
        }

        public TestCase build() {
            return new TestCase(id, name, description, precondition, steps, expectedResult, actualResult, status, priority, moduleId, projectId, creatorId, executorId, createTime, updateTime, deleted);
        }
    }
} 