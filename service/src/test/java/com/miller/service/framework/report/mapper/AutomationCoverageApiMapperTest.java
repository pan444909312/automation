package com.miller.service.framework.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.mapper.report.AutoCaseRoiMapper;
import com.miller.service.framework.report.AutoDBUtils;
import com.miller.service.framework.util.TestCaseUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutomationCoverageApiMapperTest {
    private static SqlSession automationSession = AutoDBUtils.getDBOfAutomationTest();
    AutomationCoverageApiMapper mapper = automationSession.getMapper(AutomationCoverageApiMapper.class);

    @Test
    void testSelectByPath() {
        // 准备测试数据
        String testCasePath = "/api/user/combine/login";
        List<AutomationCoverageApiEntity> automationCoverageApiEntities = mapper.selectByPath(testCasePath);
        automationCoverageApiEntities.forEach(System.out::println);
    }

    @Disabled
    @Test
    void testUpdateByPath() {
        // 测试数据
        String testCasePath = "/api/user/combine/login";
        AutomationCoverageApiEntity automationCoverageApiEntity = new AutomationCoverageApiEntity();
        automationCoverageApiEntity.setIsAutomation(1);
        automationCoverageApiEntity.setLastExecuteTime(System.currentTimeMillis());
        automationCoverageApiEntity.setExecutor(TestCaseUtils.getExecutor());
        automationCoverageApiEntity.setTestCasePath(testCasePath);
        automationCoverageApiEntity.setTestCaseRequestLast("{\"name\":\"test\"}");
        automationCoverageApiEntity.setTestCaseResponseLast("{\"code\":200}");
//        int update = mapper.updateByPath(testCasePath, automationCoverageApiEntity);
//        System.out.println(update);
    }

}