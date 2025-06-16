package com.testhub.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testhub.backend.dto.TestCaseDTO;
import com.testhub.backend.entity.TestCase;
import com.testhub.backend.entity.User;
import com.testhub.backend.repository.TestCaseRepository;
import com.testhub.backend.repository.UserRepository;
import com.testhub.backend.service.TestCaseService;
import com.testhub.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试用例服务实现类
 */
@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public List<TestCaseDTO> findAll() {
        return testCaseRepository.selectList(null)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TestCaseDTO findById(Long id) {
        TestCase testCase = testCaseRepository.selectById(id);
        return testCase != null ? convertToDTO(testCase) : null;
    }

    @Override
    public List<TestCaseDTO> findByProjectId(Long projectId) {
        return testCaseRepository.selectList(
                new LambdaQueryWrapper<TestCase>()
                        .eq(TestCase::getProjectId, projectId)
        ).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestCaseDTO> findByModuleId(Long moduleId) {
        return testCaseRepository.selectList(
                new LambdaQueryWrapper<TestCase>()
                        .eq(TestCase::getModuleId, moduleId)
        ).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TestCaseDTO create(TestCaseDTO testCaseDTO) {
        TestCase testCase = new TestCase();
        BeanUtils.copyProperties(testCaseDTO, testCase);
        
        // 设置默认值
        testCase.setStatus(0); // 未开始
        testCase.setDeleted(0);
        
        // 设置创建者
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            testCase.setCreatorId(currentUser.getId());
        }
        
        testCaseRepository.insert(testCase);
        return convertToDTO(testCase);
    }

    @Override
    @Transactional
    public TestCaseDTO update(Long id, TestCaseDTO testCaseDTO) {
        TestCase existingTestCase = testCaseRepository.selectById(id);
        if (existingTestCase == null) {
            return null;
        }
        
        BeanUtils.copyProperties(testCaseDTO, existingTestCase);
        existingTestCase.setId(id); // 确保ID不变
        
        testCaseRepository.updateById(existingTestCase);
        return convertToDTO(existingTestCase);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        testCaseRepository.deleteById(id);
    }
    
    /**
     * 将实体转换为DTO
     */
    private TestCaseDTO convertToDTO(TestCase testCase) {
        TestCaseDTO testCaseDTO = new TestCaseDTO();
        BeanUtils.copyProperties(testCase, testCaseDTO);
        
        // 获取创建者和执行者姓名
        if (testCase.getCreatorId() != null) {
            User creator = userRepository.selectById(testCase.getCreatorId());
            if (creator != null) {
                testCaseDTO.setCreatorName(creator.getName());
            }
        }
        
        if (testCase.getExecutorId() != null) {
            User executor = userRepository.selectById(testCase.getExecutorId());
            if (executor != null) {
                testCaseDTO.setExecutorName(executor.getName());
            }
        }
        
        return testCaseDTO;
    }

    @Override
    @Transactional
    public TestCase createTestCase(TestCase testCase) {
        // 设置创建人
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            testCase.setCreatorId(currentUser.getId());
        }
        testCaseRepository.insert(testCase);
        return testCase;
    }

    @Override
    @Transactional
    public TestCase updateTestCase(TestCase testCase) {
        testCaseRepository.updateById(testCase);
        return testCase;
    }

    @Override
    @Transactional
    public boolean deleteTestCase(Long id) {
        return testCaseRepository.deleteById(id) > 0;
    }

    @Override
    public IPage<TestCase> findByPage(int page, int size, String statusStr, String moduleIdStr, String priorityStr) {
        LambdaQueryWrapper<TestCase> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (StringUtils.hasText(statusStr)) {
            try {
                Integer status = Integer.parseInt(statusStr);
                queryWrapper.eq(TestCase::getStatus, status);
            } catch (NumberFormatException e) {
                // 忽略转换错误
            }
        }
        
        if (StringUtils.hasText(moduleIdStr)) {
            try {
                Long moduleId = Long.parseLong(moduleIdStr);
                queryWrapper.eq(TestCase::getModuleId, moduleId);
            } catch (NumberFormatException e) {
                // 忽略转换错误
            }
        }
        
        if (StringUtils.hasText(priorityStr)) {
            try {
                Integer priority = Integer.parseInt(priorityStr);
                queryWrapper.eq(TestCase::getPriority, priority);
            } catch (NumberFormatException e) {
                // 忽略转换错误
            }
        }
        
        // 按创建时间倒序排序
        queryWrapper.orderByDesc(TestCase::getCreateTime);
        
        // 分页查询
        Page<TestCase> pageParam = new Page<>(page, size);
        return testCaseRepository.selectPage(pageParam, queryWrapper);
    }

    @Override
    @Transactional
    public TestCase updateStatus(Long id, Integer status) {
        TestCase testCase = testCaseRepository.selectById(id);
        if (testCase != null) {
            testCase.setStatus(status);
            
            // 如果状态为进行中(1)，设置执行人
            if (status != null && status == 1) {
                User currentUser = userService.getCurrentUser();
                if (currentUser != null) {
                    testCase.setExecutorId(currentUser.getId());
                }
            }
            
            testCaseRepository.updateById(testCase);
        }
        return testCase;
    }
} 