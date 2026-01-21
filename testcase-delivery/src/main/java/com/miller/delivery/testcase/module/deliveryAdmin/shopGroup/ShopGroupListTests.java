package com.miller.delivery.testcase.module.deliveryAdmin.shopGroup;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 商家群组-列表
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT2RPJ3DSXVMW0P838QAQXQ",
        scenarioName = "商家群组-列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("商家群组-列表")
public class ShopGroupListTests {

    @DisplayName("商家群组-列表")
    @Test
    void shouldGetShopGroupList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取商家群组列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/shopGroup/groupList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"warningPushReasonTypeList\":[],\"pageNo\":1,\"pageSize\":10,\"cityName\":\"杭州市\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 验证响应中包含"杭州市"
        String responseText = responseBody.toString();
        assert responseText.contains("杭州市") : "响应中未包含杭州市";
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

