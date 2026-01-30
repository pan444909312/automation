package com.miller.delivery.testcase.module.deliveryAdmin.CustomerService.push;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 推送管理-列表
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT2HCKQSEFC6DR8HXXVJXA8",
        scenarioName = "推送管理-列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("推送管理-列表")
public class PushManagementListTests {

    @DisplayName("推送管理-列表-默认搜索")
    @Test
    void shouldGetPushManagementListWithDefaultSearch() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取推送管理列表（默认搜索）
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/orderAbnormal/abnormalPage";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"warningPushReasonTypeList\":[],\"pageNo\":1,\"pageSize\":10,\"cityName\":\"杭州市\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 验证响应中包含"全城"
        String responseText = responseBody.toString();
        assert responseText.contains("全城") : "响应中未包含全城";
    }

    @DisplayName("推送管理-列表-筛选推送类型")
    @Test
    void shouldGetPushManagementListWithPushTypeFilter() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取推送管理列表（筛选推送类型）
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/orderAbnormal/abnormalPage";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"warningPushReasonTypeList\":[],\"warningPushType\":10,\"pageNo\":1,\"pageSize\":10,\"cityName\":\"杭州市\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 验证响应中包含"全城"
        String responseText = responseBody.toString();
        assert responseText.contains("全城") : "响应中未包含全城";
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

