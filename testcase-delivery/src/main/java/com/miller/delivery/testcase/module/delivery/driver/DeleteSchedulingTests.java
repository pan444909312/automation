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
 * 删除排班
 */
@Scenario(
        scenarioID = "01JPP77BZHDYSTQ3ZNXWRFPYPC",
        scenarioName = "骑手app-计划tab-删除排班",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("删除排班")
public class DeleteSchedulingTests {

    @DisplayName("删除时段-正常")
    @Test
    void shouldDeleteScheduling() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/delScheduling";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"areaId\":51,\"schedulingDateList\":[\"%s\"],\"startTime\":\"23:30\",\"endTime\":\"24:00\"}", nowDate);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }

    private Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();

        headers.put("content-type", "application/json");
        return headers;
    }
}


