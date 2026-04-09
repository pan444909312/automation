package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.specialOrder;

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
        scenarioID = "01KNS46YQ30M70K8QNFJ2AEPKH",
        scenarioName = "司管后台-订单管理-特殊单列表-导出特殊单",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("导出特殊单")
public class SpecialOrderImportTests {

    @DisplayName("导出特殊单")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当天日期
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 获取订单数据列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/special-order/downLoadData";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = LocalDate.now().minusDays(6).format(formatter);
        String endDate = LocalDate.now().format(formatter);

        String body = String.format("{\n" +
                "    \"duty\": null,\n" +
                "    \"city\": null,\n" +
                "    \"areaIdList\": [],\n" +
                "    \"startDate\": \""+startDate+"\",\n" +
                "    \"endDate\": \""+endDate+"\",\n" +
                "    \"specialNo\": null,\n" +
                "    \"orderSn\": null,\n" +
                "    \"operator\": null,\n" +
                "    \"orderBusinessType\": null,\n" +
                "    \"siteId\": null,\n" +
                "    \"pageNo\": 1,\n" +
                "    \"pageSize\": 10\n" +
                "}");
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }



}

