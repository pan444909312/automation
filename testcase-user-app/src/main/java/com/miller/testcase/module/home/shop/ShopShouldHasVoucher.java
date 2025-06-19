package com.miller.testcase.module.home.shop;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCV",
        scenarioName = "店铺有代金券",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 5, manualTestTime = 15)
@DisplayName("/api/user/voucher/shop/index/voucher")
public class ShopShouldHasVoucher {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/voucher/shop/index/voucher";

    @DisplayName("店铺有代金券")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        headers.put("version", "8.61.0");
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/shop/request/voucherReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/shop/response/voucherResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
