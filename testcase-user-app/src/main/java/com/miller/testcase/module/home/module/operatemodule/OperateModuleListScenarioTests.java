package com.miller.testcase.module.home.module.operatemodule;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Scenario(scenarioID = "01JWFWNA250A4C3ZW7PFCPM5CI", scenarioName = "已登陆用户-获取首页资源位"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
public class OperateModuleListScenarioTests {
    private static final String uri = TestcaseConfig.HOST+"/api/user/index/operateModuleList";
    @DisplayName("已登陆用户-获取首页资源位")
    @Test
    void shouldReturnTaskSuccessfully(){
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("authorization",TestCaseHelpful.login("17700000077","123456"));
        headers.put("content-type","application/x-www-form-urlencoded");
        headers.put("uniqueToken","fa12d107ed0041ce8c81b95264044969");
        Map<String, Object> params = new HashMap<>();
        params.put("countryCode","CN");
        var responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, params, null);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.address").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.hotSearch").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.indexModuleVo.topBannerList").isNotNull();



    }
}
