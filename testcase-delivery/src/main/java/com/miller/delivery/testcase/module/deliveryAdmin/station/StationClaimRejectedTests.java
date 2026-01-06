package com.miller.delivery.testcase.module.deliveryAdmin.station;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-装备管理审核列表-已拒绝申请
 */
@Scenario(
        scenarioID = "01KDT0EXPMFDJH2Y0H3KE87MTR",
        scenarioName = "骑手列表-装备管理审核列表-已拒绝申请",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取骑手驿站配置列表-已拒绝申请装备")
public class StationClaimRejectedTests {

    @DisplayName("装备管理审核列表-已拒绝申请")
    @Test
    void shouldGetStationClaimRejectedList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取装备管理审核列表-已拒绝申请
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/equipmentClaim/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"pageNo\":1,\"pageSize\":10,\"recordStatus\":5000}";

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
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

