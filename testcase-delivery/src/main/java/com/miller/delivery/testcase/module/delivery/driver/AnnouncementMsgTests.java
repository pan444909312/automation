package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 重要公告通知
 */
@Scenario(
        scenarioID = "01JPP7XSFCWHZ5NY7R6PMYFJW5",
        scenarioName = "骑手app-消息-重要公告通知",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("重要公告通知")
public class AnnouncementMsgTests {

    @DisplayName("重要公告通知-正常")
    @Test
    void shouldGetAnnouncementList() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/message/announcementMsgList";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        String body = "{\"pageNo\":1,\"pageSize\":20}";
        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }

    @DisplayName("重要公告通知-未登录")
    @Test
    void shouldRejectWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/message/announcementMsgList";
        Map<String, Object> headers = createHeaders();

        String body = "{\"pageNo\":1,\"pageSize\":20}";
        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
    }

    private Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("apptypeid", "2");

        headers.put("Content-Type", "application/json");
        return headers;
    }
}


