package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 已读一条消息
 */
@Scenario(
        scenarioID = "01JWB1G0C312N1FS9K1SXWPKES",
        scenarioName = "骑手app-已读一条消息",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("已读一条消息")
public class ReadSingleMessageTests {

    @DisplayName("已读一条消息-正常")
    @Test
    void shouldReadSingleMessage() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/message/v2/messageRead";
        Map<String, Object> headers = createIosHeaders();
        headers.put("authorization", token);

        String body = "{\"messageId\":64303,\"messageType\":101}";
        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }

    private Map<String, Object> createIosHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("_pendingsign", "_ts1748422246980authorizationf4f26cb02c6ece1748f5345f45278096");
        headers.put("operatingsystem", "2");
        headers.put("_sign", "de7fd7c6cd4bad7c890f086cbbc0429a");
        headers.put("user-agent", "PandaDelivery/5.64.0 (iPhone; iOS 18.3.1; Scale/2.00) OKPOS");
        headers.put("_ts", "1748422246980");
        headers.put("brand", "iPhone 11");
        headers.put("latitude", "30.203602");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.64.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("longitude", "120.216758");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept", "*/*");

        headers.put("content-type", "application/json");
        return headers;
    }
}


