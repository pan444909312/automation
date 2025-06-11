package com.miller.testcase.module.home.shop;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


@Scenario(scenarioID = "01JVKR6DPZH7V8WK4B381AMV6R",
        scenarioName = "获取第二单减免的关联店铺信息",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/shop/spellList")
public class UserShopSpellListSuccess {
    private static final String uri = TestcaseConfig.HOST + "/api/user/shop/spellList";

    @DisplayName("获取第二单减免的关联店铺信息")
    @Test
    void shouldReturnSuccessfully() {
        String apiUri = uri + "?pageSize=10&shopId=764707652&categoryIdList=%5B%5D&orderSn=975141807984274006181&pageNo=1";
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/home/shop/headers/spellListHeaders.json");

        Map<String, Object> params = new HashMap<>();
        params.put("shopId",764707652);
        params.put("orderSn","975141807984274006181");
        // 给请求头添加数据，例如这里添加token
//        headers.put("authorization", TestCaseHelpful.login("13960000007", "123456"));
        String responseBody = TestCaseHelpful.sendRequest("GET", uri, params, headers, null);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/shop/response/UserShopSpellListResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
    }
}
