package com.miller.delivery.testcase.module.deliveryAdmin.equipment;

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
 * 司管后台-骑手列表-装备管打卡记录列表
 */
@Scenario(
        scenarioID = "01KDSX1AZDNP12KMA1HP7TE52M",
        scenarioName = "骑手列表-装备管打卡记录列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("装备打卡记录列表")
public class EquipmentCheckListTests {

    @DisplayName("装备打卡记录列表")
    @Test
    void shouldGetEquipmentCheckList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取装备打卡记录列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/equipmentCheck/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        // 生成日期范围（最近一周）
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateStr = startDate.format(formatter);
        String endDateStr = endDate.format(formatter);

        String body = String.format("{" +
                "\"date\":[\"%s\",\"%s\"]," +
                "\"pageNo\":1," +
                "\"pageSize\":10," +
                "\"checkDateStart\":\"%s\"," +
                "\"checkDateEnd\":\"%s\"" +
                "}", startDateStr, endDateStr, startDateStr, endDateStr);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言获取成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

