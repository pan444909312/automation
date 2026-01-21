package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 已读消息（全部）
 */
@Scenario(
        scenarioID = "01JPP8196VHSWSBYG4SWJZCJZ9",
        scenarioName = "骑手app-消息-已读消息",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("已读消息")
public class ReadMessagesTests {

    @DisplayName("已读所有消息-正常")
    @Test
    void shouldMarkAllMessagesRead() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/message/v2/messageRead";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        String body = "{\"messageId\":0,\"messageType\":100}";
        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }

    @DisplayName("已读所有消息-参数错误")
    @Test
    void shouldFailWhenParamsInvalid() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/message/v2/messageRead";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        String body = "{\"msgType\":\"\"}";
        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
    }

    @DisplayName("已读所有消息-未登录")
    @Test
    void shouldRejectWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/message/v2/messageRead";
        Map<String, Object> headers = createHeaders();

        String body = "{\"msgType\":\"100\"}";
        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
    }

    private Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("platform", "");

        headers.put("Content-Type", "application/json");
        return headers;
    }
}


