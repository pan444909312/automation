package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息汇总
 */
@Scenario(
        scenarioID = "01JPP822JT4QRM2HS5D3T5VGN1",
        scenarioName = "骑手app-消息-消息汇总",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("消息汇总")
public class MessageSummaryTests {

    @DisplayName("消息汇总-正常")
    @Test
    void shouldGetMessageSummary() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/message/messageSummary";
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("enableSign", "false");

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }
}


