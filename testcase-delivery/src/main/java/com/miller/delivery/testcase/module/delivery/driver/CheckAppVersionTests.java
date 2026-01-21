package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 检测app版本升级
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JW69MS1RB0VH12KBK52MAF5F",
        scenarioName = "骑手app-检测app版本升级",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("检测app版本升级")
public class CheckAppVersionTests {

    @DisplayName("检查app版本升级")
    @Test
    void shouldCheckAppVersion() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 检查app版本升级
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/home/checkAppVersion";
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
     * 创建骑手app请求头（IOS平台）
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("operatingsystem", "2");
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

