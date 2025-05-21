package com.miller.service.framework.report.mapper;

import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.service.framework.report.AutoDBUtils;
import com.miller.service.framework.util.TestCaseUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

class AutomationCoverageApiMapperTest {
    private static SqlSession automationSession = AutoDBUtils.getDBOfAutomationTest();
    AutomationCoverageApiMapper mapper = automationSession.getMapper(AutomationCoverageApiMapper.class);

    @Test
    void testSelectByPath() {
        // 准备测试数据
        String testCaseRequestPath = "/api/user/combine/login";
        List<AutomationCoverageApiEntity> automationCoverageApiEntities = mapper.selectByPath(testCaseRequestPath);
        automationCoverageApiEntities.forEach(System.out::println);
    }

    @Disabled
    @Test
    void testUpdateByPath() {
        // 测试数据
        String testCaseRequestPath = "/api/user/combine/login";
        AutomationCoverageApiEntity automationCoverageApiEntity = new AutomationCoverageApiEntity();
        automationCoverageApiEntity.setIsAutomation(1);
        automationCoverageApiEntity.setLastExecuteTime(System.currentTimeMillis());
        automationCoverageApiEntity.setExecutor(TestCaseUtils.getExecutor());
        automationCoverageApiEntity.setTestCaseRequestPath(testCaseRequestPath);
        automationCoverageApiEntity.setTestCaseRequestMethod("POST");
        automationCoverageApiEntity.setTestCaseRequestBody("{\"username\":\"admin\",\"password\":\"123456\"}");
        automationCoverageApiEntity.setTestCaseRequestUri("http://lcoalhost/api/user/combine/login");
        automationCoverageApiEntity.setTestCaseRequestHeaders("{\"Content-Type\":\"application/json\"}");
        automationCoverageApiEntity.setTestCaseResponseBody("{\"code\":200}");

//        int update = mapper.updateByPath(testCaseRequestPath, automationCoverageApiEntity);
//        System.out.println(update);
    }

}