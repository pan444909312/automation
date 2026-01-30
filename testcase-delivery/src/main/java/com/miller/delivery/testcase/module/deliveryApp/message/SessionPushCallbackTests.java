package com.miller.delivery.testcase.module.deliveryApp.message;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 会话推送回调
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01K9VE9ZJ3E8EKBE5NT93Q9Y0H",
        scenarioName = "会话推送回调",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("会话推送回调")
public class SessionPushCallbackTests {

    @DisplayName("会话推送回调")
    @Test
    void shouldHandleSessionPushCallback() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 会话推送回调
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/uDesk/callBackPush";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"web_token\":\"DRIVER-UDESK-UKGB2711221619\"," +
                "\"messages\":[{\"type\":\"message\",\"message_id\":\"udesk_8561762919969593\"," +
                "\"agent_id\":421,\"agent_name\":\"Northern Dispatcher\",\"im_sub_session_id\":7416021," +
                "\"message_created_at\":\"2025-11-12 11:59:29\"," +
                "\"data\":{\"content\":\"会话结束，祝您愉快！The session is over, have a nice day! \"," +
                "\"msg_close\":true}}]}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("未登录-会话推送回调")
    @Test
    void shouldFailWhenNotLoggedIn() {
        // 未登录时调用接口
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/uDesk/callBackPush";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置 authorization
        
        String body = null;
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.217095");
        headers.put("latitude", "30.203565");
        headers.put("operatingsystem", "1");
        headers.put("platform", "");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json");
        return headers;
    }
}

