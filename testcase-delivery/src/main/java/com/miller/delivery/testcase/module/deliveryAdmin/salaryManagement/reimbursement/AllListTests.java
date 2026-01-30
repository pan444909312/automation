package com.miller.delivery.testcase.module.deliveryAdmin.salaryManagement.reimbursement;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KDQ96N0NKJ02H2PGSKM42S7A",
        scenarioName = "骑手个人薪资全部tab数据",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取全部tab下的数据")
public class AllListTests {

    @DisplayName("获取全部tab数据")
    @Test
    void shouldGetAllList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 全部tab默认数据
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reimbursement/allList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = "{\"cityList\":[],\"pageNo\":1,\"pageSize\":10}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri, null, headers, body1);

        // 3) 断言第一个请求
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");
        // 验证返回的数据大于0
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("data.totalNum").isNotNull();

        // 4) 全部tab-查询杭州下的数据
        String body2 = "{\"cityList\":[\"杭州市\"],\"pageNo\":1,\"pageSize\":10}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri, null, headers, body2);

        // 5) 断言第二个请求
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
        // 验证返回的数据大于0
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("data.totalNum").isNotNull();
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

