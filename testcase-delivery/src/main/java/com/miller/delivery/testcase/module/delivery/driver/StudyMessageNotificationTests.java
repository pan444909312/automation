package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 学习资料通知
 */
@Scenario(
        scenarioID = "01JPP80FW5RX48RFKBERPDT6DK",
        scenarioName = "骑手app-消息-学习资料通知",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("学习资料通知")
public class StudyMessageNotificationTests {

    @DisplayName("学习资料通知-正常")
    @Test
    void shouldGetStudyMessageList() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/message/studyMsgList";
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", token);

        headers.put("Content-Type", "application/json");

        String body = "{\"pageNo\":1,\"pageSize\":10}";
        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }
}


