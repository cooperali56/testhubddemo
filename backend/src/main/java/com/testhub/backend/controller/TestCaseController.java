package com.testhub.backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.testhub.backend.dto.TestCaseDTO;
import com.testhub.backend.entity.TestCase;
import com.testhub.backend.service.TestCaseService;
import com.testhub.backend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 测试用例控制器
 */
@Tag(name = "测试用例接口", description = "测试用例管理相关接口")
@RestController
@RequestMapping("/api/testcase")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    /**
     * 获取所有测试用例
     */
    @Operation(summary = "获取所有测试用例", description = "获取系统中的所有测试用例")
    @GetMapping
    public Result<List<TestCaseDTO>> findAll() {
        return Result.success(testCaseService.findAll());
    }

    /**
     * 根据ID获取测试用例
     */
    @Operation(summary = "获取测试用例详情", description = "根据ID获取测试用例详细信息")
    @GetMapping("/{id}")
    public Result<TestCaseDTO> findById(
            @Parameter(description = "测试用例ID", required = true) 
            @PathVariable Long id) {
        TestCaseDTO testCaseDTO = testCaseService.findById(id);
        return testCaseDTO != null ? Result.success(testCaseDTO) : Result.notFound("测试用例不存在");
    }

    /**
     * 根据项目ID获取测试用例
     */
    @Operation(summary = "获取项目测试用例", description = "获取指定项目下的所有测试用例")
    @GetMapping("/project/{projectId}")
    public Result<List<TestCaseDTO>> findByProjectId(
            @Parameter(description = "项目ID", required = true) 
            @PathVariable Long projectId) {
        return Result.success(testCaseService.findByProjectId(projectId));
    }

    /**
     * 根据模块ID获取测试用例
     */
    @Operation(summary = "获取模块测试用例", description = "获取指定模块下的所有测试用例")
    @GetMapping("/module/{moduleId}")
    public Result<List<TestCaseDTO>> findByModuleId(
            @Parameter(description = "模块ID", required = true) 
            @PathVariable Long moduleId) {
        return Result.success(testCaseService.findByModuleId(moduleId));
    }

    /**
     * 创建测试用例
     */
    @Operation(summary = "创建测试用例", description = "创建新的测试用例")
    @PostMapping
    public Result<TestCaseDTO> create(
            @Parameter(description = "测试用例信息", required = true) 
            @Valid @RequestBody TestCaseDTO testCaseDTO) {
        return Result.success(testCaseService.create(testCaseDTO));
    }

    /**
     * 更新测试用例
     */
    @Operation(summary = "更新测试用例", description = "更新已有的测试用例信息")
    @PutMapping("/{id}")
    public Result<TestCaseDTO> update(
            @Parameter(description = "测试用例ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "测试用例信息", required = true) 
            @Valid @RequestBody TestCaseDTO testCaseDTO) {
        TestCaseDTO updatedTestCase = testCaseService.update(id, testCaseDTO);
        return updatedTestCase != null ? Result.success(updatedTestCase) : Result.notFound("测试用例不存在");
    }

    /**
     * 删除测试用例
     */
    @Operation(summary = "删除测试用例", description = "删除指定的测试用例")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "测试用例ID", required = true) 
            @PathVariable Long id) {
        testCaseService.delete(id);
        return Result.success();
    }

    /**
     * 分页查询测试用例
     */
    @Operation(summary = "分页查询测试用例", description = "根据条件分页查询测试用例")
    @GetMapping("/page")
    public Result<IPage<TestCase>> findByPage(
            @Parameter(description = "页码") 
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "每页大小") 
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "状态") 
            @RequestParam(required = false) String status,
            @Parameter(description = "模块ID") 
            @RequestParam(required = false) String moduleId,
            @Parameter(description = "优先级") 
            @RequestParam(required = false) String priority) {
        return Result.success(testCaseService.findByPage(page, size, status, moduleId, priority));
    }

    /**
     * 更新测试用例状态
     */
    @Operation(summary = "更新测试用例状态", description = "更新指定测试用例的状态")
    @PatchMapping("/{id}/status/{status}")
    public Result<TestCase> updateStatus(
            @Parameter(description = "测试用例ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "状态", required = true) 
            @PathVariable Integer status) {
        TestCase testCase = testCaseService.updateStatus(id, status);
        return testCase != null ? Result.success(testCase) : Result.notFound("测试用例不存在");
    }
} 