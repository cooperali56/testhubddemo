package com.testhub.backend.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testhub.backend.entity.TestCase;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试用例数据访问层
 */
@Mapper
public interface TestCaseRepository extends BaseMapper<TestCase> {
} 