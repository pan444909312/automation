package com.miller.testcase.module.activity.theme.commonchannel;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWCZVXDASV9FCE7F774FJR2Y", scenarioName = "未登录用户-首页时段频道楼层"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class ThemeCommonChannelShopListScenarioTests {
    private static final String uri = TestcaseConfig.HOST + "/api/app/user/theme/commonChannel/shop/list";

    @DisplayName("未登录用户-首页时段频道楼层")
    @Test
    void shouldReturnShopSuccessfully() {
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/activity/theme/commonchannel/request/success.json");
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.resultCode").isEqualTo(1000);// 使用 JsonPath 方式
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.shopIndexVOS").isArray().isNotEmpty();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.headerVO").isNotNull();


    }
}
