package com.miller.service.testcase;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.constant.RunTeatCaseTypeEnum;
import com.miller.entity.testcase.TestCaseEntity;

import java.util.List;

/**
 * 测试用例 Service
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/3 11:12:53
 */
public interface TestCaseService extends IService<TestCaseEntity> {
    /**
     * 运行测试用例
     * @param packageNameList 包名
     * @return 测试用例数量
     */
    String runTestCase(List<String> packageNameList, RunTeatCaseTypeEnum type);

    String runTestCaseC(List<String> packageNameList, RunTeatCaseTypeEnum type);

    String runTestCaseD(List<String> packageNameList, RunTeatCaseTypeEnum type);

}
