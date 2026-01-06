package com.miller.delivery.testcase.module.deliveryAdmin.control;

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
 * 司管后台-骑手处罚列表-批量解除处罚
 */
@Scenario(
        scenarioID = "01KDQH8X5N2JYV0MCP6GR8X8Y",
        scenarioName = "骑手处罚列表-批量解除处罚",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("批量解除处罚")
public class PunishBatchRelieveTests {

    @DisplayName("批量解除处罚")
    @Test
    void shouldBatchRelievePunish() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 批量解除处罚
        batchRelievePunish(token);
    }

    private void batchRelievePunish(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/relieve";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        // 生成日期范围（最近一周）
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String updateTimeStartStr = startDate.format(formatter);
        String updateTimeEndStr = endDate.format(formatter);

        String body = String.format("{\"relieveRemark\":\"自动化测试解除\",\"relieveType\":4,\"cityNameList\":[],\"areaIdList\":[],\"updateTimeStartStr\":\"%s\",\"updateTimeEndStr\":\"%s\",\"controlReason\":[],\"driverId\":\"1398714150\",\"driverName\":\"\",\"driverTel\":\"\",\"gradeTypeList\":[],\"punishActionList\":[],\"punishStatusList\":[],\"driverBusinessType\":null,\"punishNo\":null}",
                updateTimeStartStr, updateTimeEndStr);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

