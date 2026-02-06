package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.capacity;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KGRRNG8R9J2SAYZM2MMJA35Y",
        scenarioName = "司管后台-订单管理-实时看板-运力概览-运力缺口",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取运力缺口")
public class CapacityMonitorListTests {

    @DisplayName("获取运力缺口")
    @Test
    void shouldGetCapacityOverview() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取运力概览-到城市级别
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/capacity/dashBoard/monitorList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body1 = "{\"city\":\"杭州市\",\"areaList\":[],\"pageNo\":1,\"pageSize\":10,\"cityList\":[\"杭州市\"]}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 3) 等待5秒
        Thread.sleep(5000);

        // 4) 获取运力概览-到商圈级别
        String body2 = "{\"city\":\"杭州市\",\"areaList\":[51],\"pageNo\":1,\"pageSize\":10,\"cityList\":[\"杭州市\"]}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");

        // 5) 等待1秒
        Thread.sleep(1000);
    }


}

