package com.miller.testcaseuserapp.module.redpacket;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWCZVXDASV9FCE7F774FJR31", scenarioName = "未登录用户-获取店铺可用红包"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class ShopRedpacketScenarioTests {
    private static final String uri = TestcaseConfig.HOST+"/api/user/shop/redPacket";
    @DisplayName("未登录用户-获取店铺可用红包-店铺红包成功")
    @Test
    void shouldReturnRedpacketSuccessfully(){
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        var requestBody=TestCaseHelpful.getJsonRequestBody("module/redpacket/request/success.json");
        var responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, requestBody, null);
        var expectedStr = TestCaseHelpful.getFileContent("module/redpacket/response/assert_some_fields.json");
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
}
