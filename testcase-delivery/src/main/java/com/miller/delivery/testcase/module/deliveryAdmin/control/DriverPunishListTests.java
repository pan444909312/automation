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
 * 司管后台-骑手处罚列表-获取骑手惩罚列表数据
 */
@Scenario(
        scenarioID = "01KDQC63NVDCN4DH3EDF0VXFGE",
        scenarioName = "骑手处罚列表-获取骑手惩罚列表数据",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("获取骑手惩罚列表数据")
public class DriverPunishListTests {

    @DisplayName("获取骑手惩罚列表")
    @Test
    void shouldGetPunishList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手惩罚列表
        getPunishList(token);
    }

    private void getPunishList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        // 生成日期范围（最近一周）
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStart = startDate.format(formatter);
        String dateEnd = endDate.format(formatter);
        String updateTimeStartStr = dateStart;
        String updateTimeEndStr = dateEnd;

        String body = String.format("{"
                + "\"dateRange\":[\"%s\",\"%s\"],"
                + "\"operatorTypeList\":[],"
                + "\"cityNameList\":[],"
                + "\"areaIdList\":[],"
                + "\"controlReason\":[],"
                + "\"driverId\":\"\","
                + "\"driverName\":\"\","
                + "\"driverTel\":\"\","
                + "\"gradeTypeList\":[],"
                + "\"punishActionList\":[],"
                + "\"punishStatusList\":[],"
                + "\"auditStatusList\":[],"
                + "\"driverBusinessType\":null,"
                + "\"punishNo\":null,"
                + "\"updateTimeStartStr\":\"%s\","
                + "\"updateTimeEndStr\":\"%s\","
                + "\"pageNo\":1,"
                + "\"pageSize\":15"
                + "}", dateStart, dateEnd, updateTimeStartStr, updateTimeEndStr);

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

