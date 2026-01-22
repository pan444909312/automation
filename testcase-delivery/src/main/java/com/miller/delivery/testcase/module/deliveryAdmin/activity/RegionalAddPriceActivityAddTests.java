package com.miller.delivery.testcase.module.deliveryAdmin.activity;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管-区域加价-新增
 *
 * Apifox: docs/d-apifox/toCheck/新增区域加价活动.apifox-cli.json
 */
@Scenario(
        scenarioID = "01K5ZPGPCTQ7X8CR1FF9ZRMNKY",
        scenarioName = "司管-区域加价-新增",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("新增区域加价活动")
public class RegionalAddPriceActivityAddTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("创建商圈加价活动-成功新增")
    @Test
    void shouldCreateRegionalAddPriceActivitySuccessfully() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String activityName = "自动化创建加价活动" + todayDate;

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/addSubmitRewardContinue";
        String body = String.format("{\"driverScope\":0,\"useFor\":5,\"scene\":3,\"target\":2,\"targetValue\":1,\"mapShow\":1,\"customerAreaIdList\":[],\"checkedGroupIdList\":[],\"excludeGroupIdList\":[],\"excludeGroupList\":[],\"checkedGroupList\":[],\"rewardTypeDesc\":\"\",\"tobaccoAlcoholType\":0,\"cityId\":1,\"city\":\"杭州市\",\"areaIdList\":[51],\"driverType\":0,\"driverRunType\":null,\"name\":\"%s\",\"rewardType\":2,\"rewardMethod\":0,\"diffDistance\":0,\"distanceRuleList\":[{}],\"rule\":1,\"startDate\":\"%s\",\"endDate\":\"%s\",\"startTime\":\"00:07:07\",\"endTime\":\"23:07:07\",\"budget\":111,\"rewardReason\":\"自动化创建%s\",\"pushMsg\":null,\"deliveryOrderType\":0,\"orderTargetType\":0,\"tobaccoType\":0,\"shopDataType\":0,\"shopGroupList\":[],\"shopGroupIdList\":[]}",
                activityName, todayDate, todayDate, todayDate);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");

        // 数据库校验（按 apifox：查询是否已新增）
        Map<String, Object> result = PandaTestDBHelpful.executeSelectOneSql(
                String.format("select count(0) as count from panda_test.hp_delivery_reward where `name`='%s' order by id desc", activityName));
        Object countObj = result.get("count");
        assert countObj != null : "数据库未返回 count";
        long count = Long.parseLong(countObj.toString());
        assert count > 0 : "数据库中未查询到新增的加价活动";
    }

    @DisplayName("创建商圈加价活动-活动名称为空")
    @Test
    void shouldFailWhenActivityNameEmpty() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/addSubmitRewardContinue";
        String body = String.format("{\"driverScope\":0,\"useFor\":5,\"scene\":3,\"target\":2,\"targetValue\":1,\"mapShow\":1,\"customerAreaIdList\":[],\"checkedGroupIdList\":[],\"excludeGroupIdList\":[],\"excludeGroupList\":[],\"checkedGroupList\":[],\"rewardTypeDesc\":\"\",\"tobaccoAlcoholType\":0,\"cityId\":1,\"city\":\"杭州市\",\"areaIdList\":[51],\"driverType\":0,\"driverRunType\":null,\"name\":\"\",\"rewardType\":2,\"rewardMethod\":0,\"diffDistance\":0,\"distanceRuleList\":[{}],\"rule\":1,\"startDate\":\"%s\",\"endDate\":\"%s\",\"startTime\":\"00:07:07\",\"endTime\":\"23:07:07\",\"budget\":111,\"rewardReason\":\"自动化创建%s\",\"pushMsg\":null,\"deliveryOrderType\":0,\"orderTargetType\":0,\"tobaccoType\":0,\"shopDataType\":0,\"shopGroupList\":[],\"shopGroupIdList\":[]}",
                todayDate, todayDate, todayDate);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("加价命名信息为空");
    }

    @DisplayName("创建商圈加价活动-城市为空")
    @Test
    void shouldFailWhenCityEmpty() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/addSubmitRewardContinue";
        String body = String.format("{\"driverScope\":0,\"useFor\":5,\"scene\":3,\"target\":2,\"targetValue\":1,\"mapShow\":1,\"customerAreaIdList\":[],\"checkedGroupIdList\":[],\"excludeGroupIdList\":[],\"excludeGroupList\":[],\"checkedGroupList\":[],\"rewardTypeDesc\":\"\",\"tobaccoAlcoholType\":0,\"cityId\":1,\"city\":\"\",\"areaIdList\":[51],\"driverType\":0,\"driverRunType\":null,\"name\":\"自动化创建加价活动%s\",\"rewardType\":2,\"rewardMethod\":0,\"diffDistance\":0,\"distanceRuleList\":[{}],\"rule\":1,\"startDate\":\"%s\",\"endDate\":\"%s\",\"startTime\":\"00:07:07\",\"endTime\":\"23:07:07\",\"budget\":111,\"rewardReason\":\"自动化创建%s\",\"pushMsg\":null,\"deliveryOrderType\":0,\"orderTargetType\":0,\"tobaccoType\":0,\"shopDataType\":0,\"shopGroupList\":[],\"shopGroupIdList\":[]}",
                todayDate, todayDate, todayDate, todayDate);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("请选择-城市信息");
    }

    @DisplayName("创建商圈加价活动-商圈为空（区域为空）")
    @Test
    void shouldFailWhenAreaIdListEmpty() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/addSubmitRewardContinue";
        String body = String.format("{\"driverScope\":0,\"useFor\":5,\"scene\":3,\"target\":2,\"targetValue\":1,\"mapShow\":1,\"customerAreaIdList\":[],\"checkedGroupIdList\":[],\"excludeGroupIdList\":[],\"excludeGroupList\":[],\"checkedGroupList\":[],\"rewardTypeDesc\":\"\",\"tobaccoAlcoholType\":0,\"cityId\":1,\"city\":\"杭州市\",\"areaIdList\":[],\"driverType\":0,\"driverRunType\":null,\"name\":\"自动化创建加价活动%s\",\"rewardType\":2,\"rewardMethod\":0,\"diffDistance\":0,\"distanceRuleList\":[{}],\"rule\":1,\"startDate\":\"%s\",\"endDate\":\"%s\",\"startTime\":\"00:07:07\",\"endTime\":\"23:07:07\",\"budget\":111,\"rewardReason\":\"自动化创建%s\",\"pushMsg\":null,\"deliveryOrderType\":0,\"orderTargetType\":0,\"tobaccoType\":0,\"shopDataType\":0,\"shopGroupList\":[],\"shopGroupIdList\":[]}",
                todayDate, todayDate, todayDate, todayDate);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("请选择-区域信息");
    }

    @DisplayName("创建商圈加价活动-活动时间为空（startTime为空）")
    @Test
    void shouldFailWhenStartTimeEmpty() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/addSubmitRewardContinue";
        String body = String.format("{\"driverScope\":0,\"useFor\":5,\"scene\":3,\"target\":2,\"targetValue\":1,\"mapShow\":1,\"customerAreaIdList\":[],\"checkedGroupIdList\":[],\"excludeGroupIdList\":[],\"excludeGroupList\":[],\"checkedGroupList\":[],\"rewardTypeDesc\":\"\",\"tobaccoAlcoholType\":0,\"cityId\":1,\"city\":\"杭州市\",\"areaIdList\":[51],\"driverType\":0,\"driverRunType\":null,\"name\":\"自动化创建加价活动%s\",\"rewardType\":2,\"rewardMethod\":0,\"diffDistance\":0,\"distanceRuleList\":[{}],\"rule\":1,\"startDate\":\"%s\",\"endDate\":\"%s\",\"startTime\":\"\",\"endTime\":\"23:07:07\",\"budget\":111,\"rewardReason\":\"自动化创建%s\",\"pushMsg\":null,\"deliveryOrderType\":0,\"orderTargetType\":0,\"tobaccoType\":0,\"shopDataType\":0,\"shopGroupList\":[],\"shopGroupIdList\":[]}",
                todayDate, todayDate, todayDate, todayDate);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("请选择-生效时间范围");
    }
}

