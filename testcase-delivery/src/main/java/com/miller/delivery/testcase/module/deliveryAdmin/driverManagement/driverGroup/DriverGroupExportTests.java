package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driverGroup;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-新增群组
 */
@Scenario(
        scenarioID = "01KGRXG5X2T07MCM4EEX4GVW2X",
        scenarioName = "导出群组",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("导出群组")
public class DriverGroupExportTests {

    private static final Long DRIVER_ID = 1398714012L; // 假设一个driverId

    @DisplayName("导出群组")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        DriverGroupAddTests driverGroupAddTests = new DriverGroupAddTests();
        int groupId = driverGroupAddTests.add(token);
        delete(token, groupId);
    }

    public void delete(String token, int groupId) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/driverGroupExport";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"groupId\":\"" + groupId + "\"}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");



    }
}

