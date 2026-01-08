package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 班表信息页面
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
@DisplayName("班表信息页面")
public class SchedulingInfoPageTests {

    @DisplayName("班表主信息页面")
    @Test
    void shouldGetSchedulingInfo() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 班表主信息页面
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/schedulingInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        // 使用固定日期（JSON中使用的是2025-09-04）
        String schedulingDate = "2025-09-04";
        String body = String.format("{\"schedulingDate\":\"%s\"}", schedulingDate);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    @DisplayName("异常case：参考为空")
    @Test
    void shouldGetSchedulingInfoWithEmptyDate() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 班表主信息页面（schedulingDate为空）
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/schedulingInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"schedulingDate\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216795");
        headers.put("latitude", "30.203471");
        headers.put("version", "5.73.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "7b0169d78de40e6e");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

