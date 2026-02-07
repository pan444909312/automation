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
 * 批量预约排班
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP78AJXWHSQTC52RWP1TP9W",
        scenarioName = "骑手app-计划tab-批量预约排班",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("批量预约排班")
public class BatchScheduleBookingTests {

    @DisplayName("相同时段的日期V2")
    @Test
    void shouldGetSameTimeDateList() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 相同时段的日期V2
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/sameTimeDateList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        // 前置操作：计算当天日期（JSON中的prerequest脚本）
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"optType\":0,\"schedulingType\":0,\"areaId\":51,\"schedulingDate\":\"%s\",\"startTime\":\"22:30\",\"endTime\":\"24:00\"}", nowDate);
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

    @DisplayName("相同时段的日期V2--参数为空")
    @Test
    void shouldGetSameTimeDateListWithEmptyParams() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 相同时段的日期V2（startTime和endTime为空）
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/sameTimeDateList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"optType\":0,\"schedulingType\":0,\"areaId\":51,\"schedulingDate\":\"%s\",\"startTime\":\"\",\"endTime\":\"\"}", nowDate);
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

    @DisplayName("相同时段的日期V2--未登录")
    @Test
    void shouldGetSameTimeDateListWithoutAuth() {
        // 注意：此用例测试未登录场景，不需要登录

        // 相同时段的日期V2（无authorization）
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/sameTimeDateList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"optType\":0,\"schedulingType\":0,\"areaId\":51,\"schedulingDate\":\"%s\",\"startTime\":\"\",\"endTime\":\"\"}", nowDate);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("platform", "");

        headers.put("Content-Type", "application/json");
        return headers;
    }
}

