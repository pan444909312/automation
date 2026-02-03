package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driverLayer;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-删除分层
 */
@Scenario(
        scenarioID = "01K602N0X8X8EKYA045Z6TR3XT",
        scenarioName = "司管-删除分层",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("删除分层")
public class DriverLayerDeleteTests {

    private static final Long LAYER_CONFIG_ID = 12345L; // 假设一个layer_config_id

    @DisplayName("删除分层")
    @Test
    void shouldDeleteDriverLayer() {
        DriverLayerAddTests driverLayerAddTests = new DriverLayerAddTests();
        Integer layer = driverLayerAddTests.addLayer();
        delete(layer);

    }
    public void delete(int id){
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 删除分层
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/delete/" + id;
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, null);

        // 3) 断言删除成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("删除分层-重复删除")
    @Test
    void shouldFailToDeleteDriverLayerTwice() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 重复删除分层
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/delete/" + LAYER_CONFIG_ID;
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, null);

        // 3) 断言接口返回包含：查询不到对应的数据
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(9999);
        String message = TestCaseHelpful.extractValue(responseBody, "$.message").toString();
        assert message.contains("查询不到对应的数据") : "应该返回查询不到对应的数据";
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Authorization", token);
        headers.put("Connection", "keep-alive");
        headers.put("Origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("Referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-site");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");

        return headers;
    }
}

