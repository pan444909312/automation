package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.DriverOffline;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 骑手下线（已完成多case）
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KGRBDK9CCKMJGCDFW97JMHRM",
        scenarioName = "关闭/开启城市级别的配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("关闭/开启城市级别的配置）")
public class SwitchCollectionCodeTests {

    @DisplayName("关闭/开启城市级别的配置")
    @Test
    void shouldCompleteOfflineFlow() {


        // ========== 第三部分：调度分配流程 ==========
        // 步骤9: 司管登录获取token
        String siGuanToken = erpLogin();


        // 步骤8: 开启到店距离限制
        switchCollectionCode(siGuanToken, 0,"city_function_on_shop_take_meal_distance");
//        送达距离开关，0关闭，1开启
        switchCollectionCode(siGuanToken, 0,"city_function_deliver_distance");
        //收餐码开关，0关闭，1开启
        switchCollectionCode(siGuanToken, 0,"city_function_meal_collection_code_switch");


    }
    private void switchCollectionCode(String siGuanToken, int switchType,String switchCode) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/sysCityConfig/switch";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);

        String body = String.format("{\"city\":\"杭州市\",\"functionKey\":\"%s\",\"switchType\":%d}", switchCode,switchType);


        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        // apifox未给明确code断言，这里按通用message=成功
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }


    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("content-type", "application/json;charset=UTF-8");
         
        return headers;
    }
}

