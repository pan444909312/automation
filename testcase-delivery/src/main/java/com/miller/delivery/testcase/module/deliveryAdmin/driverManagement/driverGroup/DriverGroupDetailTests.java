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
        scenarioID = "01KGRXG59814X6NP0NVQG3FMHB",
        scenarioName = "查看群组",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查看群组")
public class DriverGroupDetailTests {

    private static final Long DRIVER_ID = 1398714012L; // 假设一个driverId

    @DisplayName("查看群组")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        DriverGroupAddTests driverGroupAddTests = new DriverGroupAddTests();
        int groupId = driverGroupAddTests.add(token);
        detail(token, groupId);
        DriverGroupdeleteTests driverGroupdeleteTests = new DriverGroupdeleteTests();
        driverGroupdeleteTests.delete(token,groupId);
    }

    public void detail(String token, int groupId) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/driverGroupDetail";
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

