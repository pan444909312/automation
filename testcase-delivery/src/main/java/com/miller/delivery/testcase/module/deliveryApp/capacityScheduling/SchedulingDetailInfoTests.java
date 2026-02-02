package com.miller.delivery.testcase.module.deliveryApp.capacityScheduling;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 查看详情
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP74G6YT1D3A60H8ZMSBK56",
        scenarioName = "骑手app-计划tab-查看详情",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查看详情")
public class SchedulingDetailInfoTests {

    @DisplayName("预约排班-详情")
    @Test
    void shouldGetSchedulingDetailInfo() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 预约排班-详情
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/schedulingDetailInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        
        // 前置操作：计算当天日期（JSON中的prerequest脚本）
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"schedulingType\":0,\"areaId\":51,\"schedulingDate\":\"%s\",\"startTime\":\"22:30\",\"endTime\":\"24:00\"}", nowDate);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    @DisplayName("预约排班-详情--参数为空")
    @Test
    void shouldGetSchedulingDetailInfoWithEmptyParams() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 预约排班-详情（startTime和endTime为空）
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/schedulingDetailInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"schedulingType\":3,\"areaId\":51,\"schedulingDate\":\"%s\",\"startTime\":\"\",\"endTime\":\"\"}", nowDate);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    @DisplayName("未登录-预约排班-详情")
    @Test
    void shouldGetSchedulingDetailInfoWithoutAuth() {
        // 注意：此用例测试未登录场景，不需要登录

        // 预约排班-详情（无authorization）
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/schedulingDetailInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();

        // 不设置authorization
        
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"schedulingType\":0,\"areaId\":51,\"schedulingDate\":\"%s\",\"startTime\":\"22:30\",\"endTime\":\"24:00\"}", nowDate);
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

        headers.put("longitude", "120.2168953");
        headers.put("latitude", "30.2035072");
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

        headers.put("content-type", "application/json;charset=UTF-8");

        return headers;
    }
}

