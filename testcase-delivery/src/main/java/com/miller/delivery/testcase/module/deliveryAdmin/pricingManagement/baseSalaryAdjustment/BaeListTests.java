package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.baseSalaryAdjustment;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-补贴调整配置
 */
@Scenario(
        scenarioID = "01KGS6TYH7V6CGS8587NXS5R9W",
        scenarioName = "获取基配调整配置列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取基配调整配置列表")
public class BaeListTests {

    private static final Long DRIVER_ID = 1398714012L; // 假设一个driverId

    @DisplayName("获取基配调整配置列表")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        list(token);

    }

    public void list(String token) {



        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/basePriceAdjustConfig/list";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"cityList\":[],\"pageNo\":1,\"pageSize\":10}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);


        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");


    }

}