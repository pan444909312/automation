package com.miller.delivery.testcase.module.deliveryFee.eta;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * ETA算法数据提供接口
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K8W69K7AQW5R3S4116TQEZ9M",
        scenarioName = "对外接口-fee服务-eta接口",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("ETA算法数据提供接口")
public class EtaDeliveryTimeDataTests {

    @DisplayName("提供ETA的接口-fee-查询商家")
    @Test
    void shouldQueryEtaDeliveryTimeDataWithShop() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/eta/delivery/time/data";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/eta/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        headers.put("authorization", "a85292f0a18e8bf0551ea3fa81be995c");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/eta/request/with_shop_body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.cityConfigs[0].city", "杭州市");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.cityConfigs[0].shopIds[0]", 892716498);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    @DisplayName("提供ETA的接口-fee-只查城市")
    @Test
    void shouldQueryEtaDeliveryTimeDataByCity() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/eta/delivery/time/data";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/eta/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        headers.put("authorization", "a85292f0a18e8bf0551ea3fa81be995c");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/eta/request/city_only_body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.cityConfigs[0].city", "杭州市");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }
}

