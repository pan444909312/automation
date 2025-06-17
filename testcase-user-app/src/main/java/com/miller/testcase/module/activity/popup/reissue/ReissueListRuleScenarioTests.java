package com.miller.testcase.module.activity.popup.reissue;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWFWNA250A4C3ZW7PFCPM5CC", scenarioName = "已登陆用户-获取天降补发规则(埋点)"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
public class ReissueListRuleScenarioTests {
    private static final String uri = TestcaseConfig.HOST_APP +"/api/app/user/reissue/list/all/rule";
    @DisplayName("已登陆用户-获取天降补发规则(埋点)")
    @Test
    void shouldReturnRuleSuccessfully(){
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("Authorization",TestCaseHelpful.login("17700000066","123456"));
        var requestBody=TestCaseHelpful.getJsonRequestBody("module/activity/popup/reissue/request/success.json");
        var responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, requestBody, null);
        var expectedStr = TestCaseHelpful.getFileContent("module/activity/popup/reissue/response/assert_some_fields.json");
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
}
