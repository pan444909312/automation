package com.miller.testcaseuserapp.module.activity.theme.shippingchannel;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JWCZVXDASV9FCE7F774FJR2Z", scenarioName = "未登录用户-首页运费减免楼层"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class ThemeShippingChannelShopListScenarioTests {
    private static final String uri = TestcaseConfig.HOST+"/api/app/user/theme/shippingChannel/shop/list";
    @DisplayName("未登录用户-首页运费减免楼层")
    @Test
    public void shouldReturnRedpacketSuccessfully(){
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        var requestBody=TestCaseHelpful.getJsonRequestBody("module/activity/theme/shippingchannel/request/success.json");
        var responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, requestBody, null);
        var expectedStr = TestCaseHelpful.getFileContent("module/activity/theme/shippingchannel/response/assert_some_fields.json");
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
}
