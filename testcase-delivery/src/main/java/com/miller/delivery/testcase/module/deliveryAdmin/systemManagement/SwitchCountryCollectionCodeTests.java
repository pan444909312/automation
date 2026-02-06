package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 骑手下线（已完成多case）
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KGRC2AESMGM4A54H0S64C568",
        scenarioName = "关闭/开启国家级别的配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("关闭/开启国家级别的配置）")
public class SwitchCountryCollectionCodeTests {

    @DisplayName("关闭/开启国家级别的配置")
    @Test
    void shouldCompleteOfflineFlow() {


        // ========== 第三部分：调度分配流程 ==========
        // 步骤9: 司管登录获取token
        String siGuanToken = erpLogin();


        //报税开关，0关闭，1开启
        switchCountryCollectionCode(siGuanToken, "hp-delivery-server.us.uk.tax.config",0);


    }
    public static  void switchCountryCollectionCode(String siGuanToken, String switchCode,int switchStatus) {

        Map<String, Object> configRecords = PandaTestDBHelpful.executeSelectOneSql(
                "select * from hp_delivery_sys_config where config_key='hp-delivery-server.us.uk.tax.config'");
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到配置";

        int configvalue = Integer.parseInt((configRecords.get("config_value")).toString());
        if (configvalue!=switchStatus){

            String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/sysConfig/switchStatus";
            Map<String, Object> headers = createErpHeaders();
            headers.put("authorization", siGuanToken);

            String body = String.format("{\"configKey\":\"%s\",\"configStatus\":%d}", switchCode,switchStatus);


            var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
            // apifox未给明确code断言，这里按通用message=成功

            if (TestCaseHelpful.extractValue(responseBody, "$.code").equals(1)){
                TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
            }else {
                TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("请在一分钟后操作");
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }


    }



}

