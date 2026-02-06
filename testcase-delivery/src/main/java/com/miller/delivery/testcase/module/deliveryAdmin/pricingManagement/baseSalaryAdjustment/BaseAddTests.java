package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.baseSalaryAdjustment;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
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
        scenarioID = "01KGS6TXB9DK7PTS868JBFY1RH",
        scenarioName = "新增基配调整配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新增基配调整配置")
public class BaseAddTests {

    private static final Long DRIVER_ID = 1398714012L; // 假设一个driverId

    @DisplayName("新增基配调整配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        String id = add(token);
        BaseDeleteTests subsidyDeleteTests = new BaseDeleteTests();
        subsidyDeleteTests.delete(token,id);


    }

    public String add(String token) {



        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/basePriceAdjustConfig/save";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"configName\":\"自动化配置\",\"ruleList\":[{\"tipStart\":1,\"tipEnd\":2,\"reduceAmount\":1}],\"cityList\":[\"郑州市\"]}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);


        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");


        // 5) 从数据库查询新增的配置
        Map<String, Object> configRecords = PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_adjustment_base_price_config where  config_name='自动化配置' order by rec_id desc limit 1");
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到新增的配置";


        String adjustmentNo = (configRecords.get("config_no")).toString();
        return adjustmentNo;
    }

}