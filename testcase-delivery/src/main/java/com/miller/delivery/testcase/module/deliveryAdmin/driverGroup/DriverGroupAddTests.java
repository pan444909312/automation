package com.miller.delivery.testcase.module.deliveryAdmin.driverGroup;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-新增群组
 */
@Scenario(
        scenarioID = "01KDQP1PFH8X4QH5NJK9XWY4A7",
        scenarioName = "获取骑手群组列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新增群组")
public class DriverGroupAddTests {

    private static final Long DRIVER_ID = 1398714012L; // 假设一个driverId

    @DisplayName("新增群组")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增群组
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/saveDriverGroup";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityName\":\"奥克兰\",\"driverIdList\":[" + DRIVER_ID + "],\"groupId\":null,\"groupName\":\"自动化群组\",\"updateType\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言新增成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 获取群组列表以提取groupId
        String listUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/driverGroupList";
        String listBody = "{\"groupName\":null,\"city\":null,\"areaIdList\":[],\"groupId\":null,\"updateType\":null,\"driverId\":null,\"driverName\":null,\"driverTel\":null,\"isDel\":0,\"pageNo\":1,\"pageSize\":15}";
        var listResponseBody = TestCaseHelpful.sendRequest(method, listUri, null, headers, listBody);
        TestCaseHelpful.assertThatJson(listResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(listResponseBody)
                .node("message").isEqualTo("成功");

        // 5) 提取groupId并删除群组（清理）
        try {
            String groupId = TestCaseHelpful.extractValue(listResponseBody, "$.data.list.[0].groupId").toString();
            String deleteUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/deleteDriverGroup";
            String deleteBody = "{\"groupId\":\"" + groupId + "\"}";
            TestCaseHelpful.sendRequest(method, deleteUri, null, headers, deleteBody);
        } catch (Exception e) {
            // 忽略删除失败，可能群组不存在
        }
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

