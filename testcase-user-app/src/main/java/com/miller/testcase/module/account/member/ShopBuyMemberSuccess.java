package com.miller.testcase.module.account.member;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JY433JW3VMGH8NH4AM6T9J63",
        scenarioName = "店铺页购买会员弹框获取成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/app/user/shop/buy/member")
public class ShopBuyMemberSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/app/user/shop/buy/member";

    @DisplayName("店铺页购买会员弹框获取成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/account/member/request/ShopBuyMemberReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/account/member/response/ShopBuyMemberResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
