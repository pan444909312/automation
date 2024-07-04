package com.miller.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.TestCaseEntity;

/**
 * 测试用例 Service
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/3 11:12:53
 */
public interface TestCaseService extends IService<TestCaseEntity> {
    void debugScenarios();
}
