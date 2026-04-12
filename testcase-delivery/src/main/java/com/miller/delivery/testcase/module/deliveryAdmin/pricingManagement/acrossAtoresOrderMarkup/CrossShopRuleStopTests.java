package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.acrossAtoresOrderMarkup;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
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
        scenarioID = "01KP0HKY08N4AEFQPRK7QCCWP8",
        scenarioName = "司管后台-骑手收入定价管理-跨店订单加价-停用+启用",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("停用+启用-跨店订单加价")
public class CrossShopRuleStopTests {

    @DisplayName("停用+启用-跨店订单加价")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        AddCrossShopRuleTests addCrossShopRuleTests = new AddCrossShopRuleTests();
        String rewardSn = addCrossShopRuleTests.add(token);
        stop(token,rewardSn);
        start(token,rewardSn);
        String deleteSql = "DELETE FROM `panda_test`.`hp_delivery_cross_shop_pricing` WHERE `id` = "+rewardSn+"";
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(deleteSql);

    }

    public void stop(String token,String rewardSn){

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String activityName = "自动化创建跨店活动修改" + todayDate;

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/price/updateStatus";
        String body = String.format("{\"ruleId\":\""+rewardSn+"\",\"status\":0}");
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
    public void start(String token,String rewardSn){

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String activityName = "自动化创建跨店活动修改" + todayDate;

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/price/updateStatus";
        String body = String.format("{\"ruleId\":\""+rewardSn+"\",\"status\":1}");
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}

