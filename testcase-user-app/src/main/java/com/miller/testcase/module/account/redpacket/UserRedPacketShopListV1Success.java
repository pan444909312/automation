package com.miller.testcase.module.account.redpacket;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DDC",
        scenarioName = "获取红包商家分类列表-新版",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("/api/user/redPacket/shopList/v1")
public class UserRedPacketShopListV1Success {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/redPacket/shopList/v1";

    @DisplayName("获取红包商家分类列表-新版")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/account/redpacket/request/UserRedPacketShopListV1Req.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/account/redpacket/response/UserRedPacketShopListV1Resp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_ARRAY_ORDER,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
    }
}
