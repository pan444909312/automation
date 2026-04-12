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
        scenarioID = "01KP0HH39446CFDYZQME88ZNFR",
        scenarioName = "司管后台-骑手收入定价管理-跨店订单加价-新增",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("新增-跨店订单加价")
public class AddCrossShopRuleTests {

    @DisplayName("新增-跨店订单加价")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        String rewardSn = add(token);

        String deleteSql = "DELETE FROM `panda_test`.`hp_delivery_cross_shop_pricing` WHERE `id` = "+rewardSn+"";
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(deleteSql);

    }


    public static String add(String token){

        String deleteSql = "DELETE FROM `panda_test`.`hp_delivery_cross_shop_pricing` WHERE city='九江市'";
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(deleteSql);
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String activityName = "自动化创建跨店活动" + todayDate;

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/price/addCrossShopRule";
        String body = String.format("{\n" +
                "    \"ruleId\": null,\n" +
                "    \"ruleName\": \""+activityName+"\",\n" +
                "    \"areaName\": \"九江市区可接单\",\n" +
                "    \"areaId\": 420,\n" +
                "    \"city\": \"九江市\",\n" +
                "    \"cityId\": 508,\n" +
                "    \"ruleList\": [\n" +
                "        {\n" +
                "            \"price\": \"1\",\n" +
                "            \"shopNum\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"price\": \"2\",\n" +
                "            \"shopNum\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"price\": \"3\",\n" +
                "            \"shopNum\": 4\n" +
                "        },\n" +
                "        {\n" +
                "            \"price\": \"4\",\n" +
                "            \"shopNum\": 5\n" +
                "        },\n" +
                "        {\n" +
                "            \"price\": \"5\",\n" +
                "            \"shopNum\": 6\n" +
                "        }\n" +
                "    ]\n" +
                "}");
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
        // 数据库校验（按 apifox：查询是否已新增）
        Map<String, Object> result = PandaTestDBHelpful.executeSelectOneSql(
                String.format("select * from panda_test.hp_delivery_cross_shop_pricing where `name`='%s' order by id desc limit 1", activityName));
        Object rewardSn = result.get("id");
        return rewardSn.toString();
    }

}

