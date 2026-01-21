package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 排班计划通知
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JW8PF8Q59ETPXXQRCKZ7PHVZ",
        scenarioName = "骑手app-排班通知",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("排班计划通知")
public class SchedulingNoticeTests {

    @DisplayName("排班计划通知")
    @Test
    void shouldGetSchedulingNotice() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 排班计划通知
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/notice";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("_pendingsign", "_ts1748343547617authorizationa74a3e4a2409c95aa15c34b008d723e4");
        headers.put("operatingsystem", "2");
        headers.put("user-agent", "PandaDelivery/5.64.0 (iPhone; iOS 18.3.1; Scale/2.00) OKPOS");
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
        headers.put("Cookie", "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22196f136f4caa4b-07e7232fd42cab-37176e50-370944-196f136f4cbc89%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk2ZjEzNmY0Y2FhNGItMDdlNzIzMmZkNDJjYWItMzcxNzZlNTAtMzcwOTQ0LTE5NmYxMzZmNGNiYzg5In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%7D");
         
        headers.put("content-type", "application/json");
        return headers;
    }
}

