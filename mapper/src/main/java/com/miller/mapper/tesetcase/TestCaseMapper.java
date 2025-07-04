package com.miller.mapper.tesetcase;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.testcase.TestCaseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/3 11:25:16
 */
@Mapper
public interface TestCaseMapper extends BaseMapper<TestCaseEntity> {
}