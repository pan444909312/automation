package com.miller.delivery.testcase.module.deliveryAdmin.DeliveryAreaSettings.deliveryFee;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 平台配送费方案-列表
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT5XSV54MNY29V2VP7HGZHF",
        scenarioName = "平台配送费方案-列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("平台配送费方案-列表")
public class DeliveryFeeConfigListTests {

    @DisplayName("平台配送费方案-列表")
    @Test
    void shouldGetDeliveryFeeConfigList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取平台配送费方案列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/order/price/config/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"configId\":\"\",\"configName\":\"\",\"openStatus\":\"\",\"scope\":\"\",\"shopId\":\"\",\"shopName\":\"\",\"areaId\":[],\"distanceType\":0,\"city\":\"杭州市\",\"priceType\":1,\"pageSize\":30,\"pageNo\":1}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("Content-Type", "application/json");
        return headers;
    }
}

