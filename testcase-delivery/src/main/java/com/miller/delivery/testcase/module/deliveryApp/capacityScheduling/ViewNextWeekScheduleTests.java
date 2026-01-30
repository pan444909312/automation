package com.miller.delivery.testcase.module.deliveryApp.capacityScheduling;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 查看下周排班
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP7A2B9DG0DP3WXBRKGBSW4",
        scenarioName = "骑手app-计划tab-查看下周排班",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("查看下周排班")
public class ViewNextWeekScheduleTests {

    @DisplayName("主信息页面-请求下周的排班")
    @Test
    void shouldGetNextWeekSchedule() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 主信息页面-请求下周的排班
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/schedulingInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        // 前置操作：计算下周一日期（JSON中的prerequest脚本）
        String nextMondayDate = getNextMonday();
        String body = String.format("{\"schedulingDate\":\"%s\"}", nextMondayDate);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    @DisplayName("异常case：主信息页面-请求下周的排班-为空")
    @Test
    void shouldGetNextWeekScheduleWithEmptyDate() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 主信息页面-请求下周的排班（schedulingDate为空）
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
     * 获取下周一日期（YYYY-MM-dd格式）
     * 对应JSON中的prerequest脚本逻辑
     */
    private String getNextMonday() {
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int daysToAdd = 8 - dayOfWeek.getValue(); // 如果今天是周日，则添加1天，否则添加至下一个周一所需天数
        if (dayOfWeek == DayOfWeek.SUNDAY) {
            daysToAdd = 1;
        }
        LocalDate nextMonday = now.plusDays(daysToAdd);
        return nextMonday.format(DateTimeFormatter.ofPattern("yyyy-M-d"));
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();

        headers.put("content-type", "application/json");
        return headers;
    }
}

