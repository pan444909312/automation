package com.miller.delivery.testcase.module.deliveryAdmin.blacklist;

import com.miller.service.framework.annotation.Scenario;
import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * blacklist thirdList
 *
 * @author chenchunxia
 * @version 2.0
 * @since 2026/01/28 21:09:50
 */
@Scenario(
        scenarioID = "01KG2BJTFJGGG0THE73SKPHDG6", // 自动生成，不要修改
        scenarioName = "三方拉黑-列表",
        author = "chenchunxia@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("blacklist thirdList")
public class BlacklistThirdlistTests {

    // 1) 司管登录获取 token



    @DisplayName("获取三方拉黑列表数据")
    @Test
    void shouldSuccess() {




        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增拉黑关系失败
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/blacklist/thirdList";
        String body = "{\"pageNo\": 1,\"pageSize\": 10}";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言获取成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");



    }
    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
} 