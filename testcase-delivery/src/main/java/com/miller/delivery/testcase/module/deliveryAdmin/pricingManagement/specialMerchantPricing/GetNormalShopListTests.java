package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.specialMerchantPricing;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KP0JAQDQ3EM23KFRD9RT81NS",
        scenarioName = "司管后台-骑手收入定价管理-特殊商家定价-获取正常商家列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("特殊商家定价-获取正常商家列表")
public class GetNormalShopListTests {

    @DisplayName("特殊商家定价-获取正常商家列表")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();



        // 3) 获取订单数据列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/shop/getNormalShopList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = String.format("{\"city\":\"杭州市\",\"shopId\":null,\"shopName\":null,\"pageNo\":1,\"pageSize\":10}");
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }




}

