package com.miller.testcaseuserapp.module.activity.theme.discountchannel;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWCZVXDASV9FCE7F774FJR2X", scenarioName = "未登录用户-首页大额满减楼层"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class ThemeDiscountChannelShopListScenarioTests {
    private static final String uri = TestcaseConfig.HOST + "/api/app/user/theme/redPacket/shop/list";

    @DisplayName("未登录用户-首页大额满减楼层")
    @Test
    void shouldReturnShopSuccessfully() {
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/activity/theme/discountchannel/request/success.json");
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.resultCode").isEqualTo(1000);// 使用 JsonPath 方式
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.shopIndexVOS").isArray().isNotEmpty();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.headerVO").isNotNull();


    }
}
