package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 主页面的收入列表查询、总收益列表查询
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K9Y1F9QKD2CHKSDY776NE3WX",
        scenarioName = "主页面的收入列表查询、总收益列表查询",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("主页面的收入列表查询、总收益列表查询")
public class DriverSalaryListTests {

    @DisplayName("主页面的收入列表查询、总收益列表查询")
    @Test
    void shouldGetDriverSalaryList() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 主页面的收入列表查询、总收益列表查询
        getDriverSalaryList(driverAccessToken);

        // 3) 未登录 -主页面的收入列表查询、总收益列表查询
        getDriverSalaryListWithoutLogin();
    }

    /**
     * 主页面的收入列表查询、总收益列表查询
     */
    private void getDriverSalaryList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driverSalary/list";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"endDate\":\"\",\"offSet\":0,\"pageNo\":1,\"pageSize\":10,\"startDate\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 未登录 -主页面的收入列表查询、总收益列表查询
     */
    private void getDriverSalaryListWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driverSalary/list";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        String body = "{\"endDate\":\"\",\"offSet\":0,\"pageNo\":1,\"pageSize\":10,\"startDate\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.2168986");
        headers.put("latitude", "30.2035028");
        headers.put("version", "5.55.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "samsung");
        headers.put("uniquetoken", "34ea70ca94766bbc");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

