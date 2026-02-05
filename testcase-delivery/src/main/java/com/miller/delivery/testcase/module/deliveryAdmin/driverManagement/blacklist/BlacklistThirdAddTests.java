package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.blacklist;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * blacklist thirdList
 *
 * @author chenchunxia
 * @version 2.0
 * @since 2026/01/28 21:09:50
 */
@Scenario(
        scenarioID = "01KGPYZPZ7JM47GCHWBBBQ5YJT", // 自动生成，不要修改
        scenarioName = "三方拉黑-新增",
        author = "chenchunxia@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("新增三方拉黑")
public class BlacklistThirdAddTests {

    // 1) 司管登录获取 token



    @DisplayName("新增三方拉黑")
    @Test
    void shouldSuccess() {

        String add = add();
        BlacklistThirdRemoveTests removeTests = new BlacklistThirdRemoveTests();
        removeTests.remove(add);

    }

    public String add(){

        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增拉黑关系失败
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/blacklist/thirdBatchAdd";
        String body = "{\"dataType\":1,\"shopIdList\":[\"111\"],\"userIdList\":[],\"thirdTypeList\":[1],\"reason\":\"自动化测试\"}";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言获取成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 5) 从数据库查询新增的配置
        Map<String, Object> configRecords = PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_third_blacklist_record where reason='自动化测试' order by id desc limit 1");
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到新增的配置";


        String recordNo= (configRecords.get("record_no")).toString();
        return recordNo;
    }
    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
} 