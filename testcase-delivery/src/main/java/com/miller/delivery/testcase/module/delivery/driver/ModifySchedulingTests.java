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
 * 修改排班
 */
@Scenario(
        scenarioID = "01JPP76HN7R6NR0C2M7HHW0VW7",
        scenarioName = "骑手app-计划tab-修改排班",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("修改排班")
public class ModifySchedulingTests {

    @DisplayName("修改时段-正常")
    @Test
    void shouldModifyScheduling() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/modifyScheduling";
        Map<String, Object> headers = createHeaders();
        headers.put("Authorization", token);

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"areaId\":51,\"originEndTime\":\"24:00\",\"originStartTime\":\"23:00\",\"schedulingDateList\":[\"%s\"],\"startTime\":\"23:00\",\"endTime\":\"23:30\"}", nowDate);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }

    private Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        return headers;
    }
}


