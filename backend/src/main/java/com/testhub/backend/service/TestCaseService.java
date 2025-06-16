package com.testhub.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.testhub.backend.dto.TestCaseDTO;
import com.testhub.backend.entity.TestCase;

import java.util.List;

/**
 * 测试用例服务接口
 */
public interface TestCaseService {

    /**
     * 获取所有测试用例
     *
     * @return 测试用例列表
     */
    List<TestCaseDTO> findAll();

    /**
     * 根据ID获取测试用例
     *
     * @param id 测试用例ID
     * @return 测试用例
     */
    TestCaseDTO findById(Long id);

    /**
     * 根据项目ID获取测试用例
     *
     * @param projectId 项目ID
     * @return 测试用例列表
     */
    List<TestCaseDTO> findByProjectId(Long projectId);

    /**
     * 根据模块ID获取测试用例
     *
     * @param moduleId 模块ID
     * @return 测试用例列表
     */
    List<TestCaseDTO> findByModuleId(Long moduleId);

    /**
     * 创建测试用例
     *
     * @param testCaseDTO 测试用例信息
     * @return 创建的测试用例
     */
    TestCaseDTO create(TestCaseDTO testCaseDTO);

    /**
     * 更新测试用例
     *
     * @param id 测试用例ID
     * @param testCaseDTO 测试用例信息
     * @return 更新后的测试用例
     */
    TestCaseDTO update(Long id, TestCaseDTO testCaseDTO);

    /**
     * 删除测试用例
     *
     * @param id 测试用例ID
     */
    void delete(Long id);

    /**
     * 创建测试用例
     *
     * @param testCase 测试用例对象
     * @return 创建后的测试用例对象
     */
    TestCase createTestCase(TestCase testCase);

    /**
     * 更新测试用例
     *
     * @param testCase 测试用例对象
     * @return 更新后的测试用例对象
     */
    TestCase updateTestCase(TestCase testCase);

    /**
     * 删除测试用例
     *
     * @param id 测试用例ID
     * @return 是否删除成功
     */
    boolean deleteTestCase(Long id);

    /**
     * 分页查询测试用例
     *
     * @param page     页码
     * @param size     每页大小
     * @param status   状态
     * @param module   模块
     * @param priority 优先级
     * @return 分页测试用例列表
     */
    IPage<TestCase> findByPage(int page, int size, String status, String module, String priority);

    /**
     * 更新测试用例状态
     *
     * @param id     测试用例ID
     * @param status 状态
     * @return 更新后的测试用例对象
     */
    TestCase updateStatus(Long id, Integer status);
} 