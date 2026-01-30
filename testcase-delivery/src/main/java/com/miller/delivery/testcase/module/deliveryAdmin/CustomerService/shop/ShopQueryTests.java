package com.miller.delivery.testcase.module.deliveryAdmin.CustomerService.shop;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 查询商家
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT2Y0K9WR87P4XM55YFD60X",
        scenarioName = "商家群组-筛选商家",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("查询商家")
public class ShopQueryTests {

    private static final String SHOP_NAME = "陈春霞";
    private static final String SHOP_ID = "892716498";

    @DisplayName("按商家名称查询商家")
    @Test
    void shouldQueryShopByName() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 按商家名称查询商家
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/shop/getSimpleShopList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format(
                "{\"shopId\":null,\"shopName\":\"%s\",\"searchForm\":null,\"city\":\"杭州市\",\"isSupermarket\":\"\",\"deliveryType\":\"\"}",
                SHOP_NAME);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 4) 验证返回包含商家ID
        assert responseBody.contains(SHOP_ID) : "返回结果应包含商家ID";
    }

    @DisplayName("按商家ID查询商家")
    @Test
    void shouldQueryShopById() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 按商家ID查询商家
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/shop/getSimpleShopList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format(
                "{\"shopId\":null,\"shopName\":\"%s\",\"searchForm\":null,\"city\":\"杭州市\",\"isSupermarket\":\"\",\"deliveryType\":\"\",\"shopId\":\"%s\"}",
                SHOP_NAME, SHOP_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 4) 验证返回包含商家ID
        assert responseBody.contains(SHOP_ID) : "返回结果应包含商家ID";
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

