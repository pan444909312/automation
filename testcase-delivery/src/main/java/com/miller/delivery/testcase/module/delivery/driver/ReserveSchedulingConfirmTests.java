package com.miller.delivery.testcase.module.delivery.driver;

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
 * 预约排班确认
 */
@Scenario(
        scenarioID = "01JPP75R07CDQ60SMFBXV3Z9B2",
        scenarioName = "骑手app-计划tab-预约排班",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("预约排班-确认同意")
public class ReserveSchedulingConfirmTests {

    @DisplayName("预约排班-确认同意")
    @Test
    void shouldReserveScheduling() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/reserveSchedulingConfirm";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"areaId\":51,\"schedulingDateList\":[\"%s\"],\"startTime\":\"22:30\",\"endTime\":\"24:00\"}", nowDate);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }

    @DisplayName("预约排班-参数为空")
    @Test
    void shouldFailWithEmptyTime() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/reserveSchedulingConfirm";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"areaId\":51,\"schedulingDateList\":[\"%s\"],\"startTime\":\"\",\"endTime\":\"\"}", nowDate);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
    }

    @DisplayName("预约排班-未登录")
    @Test
    void shouldRejectWithoutAuth() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/reserveSchedulingConfirm";
        Map<String, Object> headers = createHeaders();

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"areaId\":51,\"schedulingDateList\":[\"%s\"],\"startTime\":\"22:30\",\"endTime\":\"24:00\"}", nowDate);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
    }

    private Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("platform", "");

        headers.put("Content-Type", "application/json");
        return headers;
    }
}


