package com.miller.testcase.module.account.redpacket;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K2KJ6BH2QHC2Z5CRYQC1VY95",
        scenarioName = "获取红包商家分类列表-旧版",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 15, manualTestTime = 10)
@DisplayName("/api/user/redPacket/shopList")
public class UserRedPacketShopListSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/redPacket/shopList";

    @DisplayName("获取红包商家分类列表-旧版")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/account/redpacket/request/UserRedPacketShopListReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/account/redpacket/response/UserRedPacketShopListResp.json");
        String expectedStrPart = TestCaseHelpful.getFileContent("module/account/redpacket/response/UserRedPacketShopListPartResp.json");
        Object result = TestCaseHelpful.extractValue(responseBody, "$.result.list[?(@.shopId==55995353)]");

        TestCaseHelpful.assertThat(result.toString()).isNotEqualTo("[]");
        TestCaseHelpful.assertThatJson(result.toString()).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStrPart);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
