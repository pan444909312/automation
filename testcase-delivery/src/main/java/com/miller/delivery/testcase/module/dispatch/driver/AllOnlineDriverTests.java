package com.miller.delivery.testcase.module.dispatch.driver;

import com.alibaba.fastjson.JSONArray;
import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 获取所有在线骑手
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPFKDWWTGEJRB7J33KMD13F", // 自动生成，不要修改
        scenarioName = "【主干用例】调度系统-骑手列表-获取所有在线骑手",
        author = "chenchunxia@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取所有在线骑手")
public class AllOnlineDriverTests {
    String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/driver/onlineDriverList";
    String method = "POST";
    String params = null;
    String assertFullField = "module/dispatch/driver/allOnline/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 先登录获取token
        String token = erpLogin();
        
        // 步骤2: 设置请求头
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/driver/allOnline/request/headers.json");
        headers.put("token", token);
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");


        // 步骤3: 设置请求体
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/driver/allOnline/request/body.json");
        
        // 生成当天开始时间和结束时间
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = now.withHour(23).withMinute(59).withSecond(59).withNano(999000000);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime = startOfDay.format(formatter);
        String endTime = endOfDay.format(formatter);
        
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.startDate", startTime);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.endDate", endTime);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.cityName", "杭州市");
        JSONArray deliveryTagList = new JSONArray();
        deliveryTagList.add("极速达");
        deliveryTagList.add("当日达");
        deliveryTagList.add("次日达");
        deliveryTagList.add("2H达");
        deliveryTagList.add("3H达");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.deliveryTagList", deliveryTagList);
        JSONArray orderBusinessTypeList = new JSONArray();
        orderBusinessTypeList.add(0);
        orderBusinessTypeList.add(1);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderBusinessTypeList", orderBusinessTypeList);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.driverBusinessTypeList", new JSONArray());
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.sortType", "2");

        // 步骤4: 发起请求
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        // 步骤5: 断言响应结果
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 司管后台登录并返回token
     *
     * @return token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/erp/auth/request/headers.json");

        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/erp/auth/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.password", "4f2142904392cbef6974ad0260caeb33");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.userName", "ding17058431144045523");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }
}

