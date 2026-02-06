package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.deliveryTimeout;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KGRT9X896YSFSZS7V7QQ2NSV",
        scenarioName = "司管后台-订单管理-实时看板-配送超时-距离段超时",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("配送超时-距离段超")
public class DistanceTimeOutListTests {

    @DisplayName("配送超时-距离段超")
    @Test
    void shouldGetCapacityOverview() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取运力概览-到城市级别
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/eta/delivery/timeout/list";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body1 = "{\"date\":[\"2025-07-01\",\"2025-09-29\"],\"cityList\":[\"杭州市\"],\"transMeanList\":[],\"deliveryAreaIdList\":[],\"areaIdList\":[],\"pageNo\":1,\"pageSize\":10,\"statDateStart\":\"2025-07-01\",\"statDateEnd\":\"2025-09-29\",\"excludeDuty\":0,\"excludeWeather\":0,\"statDim\":\"距离段超时\"}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 3) 等待5秒
        Thread.sleep(5000);

        // 4) 获取运力概览-到商圈级别
        String body2 = "{\"date\":[\"2025-07-01\",\"2025-09-29\"],\"cityList\":[\"杭州市\"],\"transMeanList\":[],\"deliveryAreaIdList\":[],\"areaIdList\":[51],\"pageNo\":1,\"pageSize\":10,\"statDateStart\":\"2025-07-01\",\"statDateEnd\":\"2025-09-29\",\"excludeDuty\":0,\"excludeWeather\":0,\"statDim\":\"距离段超时\"}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");

        // 5) 等待1秒
        Thread.sleep(1000);
    }


}

