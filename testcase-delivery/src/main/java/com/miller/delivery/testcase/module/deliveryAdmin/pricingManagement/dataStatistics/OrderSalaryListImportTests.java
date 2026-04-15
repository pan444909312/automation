package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.dataStatistics;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KP81HZ752D5EN07B1GHHBWXS",
        scenarioName = "司管后台-骑手收入定价管理-数据统计列表-导出",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("数据统计列表-导出")
public class OrderSalaryListImportTests {

    @DisplayName("数据统计列表-导出")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        String startDate = sevenDaysAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = today.format(DateTimeFormatter.
                ofPattern("yyyy-MM-dd"));


        // 3) 获取订单数据列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/orderSalary/exportData";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = String.format("{\n" +
                "    \"adjustment\": \"\",\n" +
                "    \"adjustmentName\": \"\",\n" +
                "    \"city\": \"\",\n" +
                "    \"driverId\": \"\",\n" +
                "    \"driverName\": \"\",\n" +
                "    \"driverPhone\": \"\",\n" +
                "    \"orderSn\": \"\",\n" +
                "    \"rewardName\": \"\",\n" +
                "    \"rewardNo\": \"\",\n" +
                "    \"arrivedTimeStart\": \""+startDate+"\",\n" +
                "    \"arrivedTimeEnd\": \""+endDate+"\",\n" +
                "    \"pageNo\": 1,\n" +
                "    \"pageSize\": 15\n" +
                "}");
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }




}

