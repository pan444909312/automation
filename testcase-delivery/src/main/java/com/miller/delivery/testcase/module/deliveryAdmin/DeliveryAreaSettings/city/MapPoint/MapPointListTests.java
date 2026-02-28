package com.miller.delivery.testcase.module.deliveryAdmin.DeliveryAreaSettings.city.MapPoint;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-城市区域配置-城市-城市地图网格点位列表获取
 */
@Scenario(
        scenarioID = "01KJHXTGZ8TXNZY2MR1M8WQSBV",
        scenarioName = "城市地图网格点位列表获取",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("城市地图网格点位列表获取")
public class MapPointListTests {
    @DisplayName("城市地图网格点位列表获取")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        mapPointTagConfig(token);
    }
    //获取城市网格标签
    public  void  mapPointTagConfig(String token) {

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/mapPoint/list";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"cityName\": \"伦敦\"\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }

}
