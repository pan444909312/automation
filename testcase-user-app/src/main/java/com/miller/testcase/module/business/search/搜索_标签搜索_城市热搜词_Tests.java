package com.miller.testcase.module.business.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 1.已配置热搜词“Payton” /api/user/index/operateModuleList.result.hotSearch
 * 2. 热搜词关联商家标签AAA
 * 3. 商家标签AAA有效：未删除、一二级标签状态均为启用
 * 4. shopId = 6666、7777关联标签AAA，且有效：未删除、已审核、普通店铺或美食城、站点、语言等
 * 5. 通过“Payton”普通搜索不到shopId = 6666、7777，可搜到shopId = 8888
 *
 * @author 潘炬翔
 * @since 2025/07/14 17:53:23
 */
@Scenario(
        scenarioID = "01K041K471DW4CEFR2347K6KRW", // 自动生成，不要修改
        scenarioName = "搜索_标签搜索_城市热搜词",
        author = "panjuxiang@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("搜索_标签搜索_城市维度")
public class 搜索_标签搜索_城市热搜词_Tests {
    String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";

    @Test
    void shouldSuccess() {
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");

        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/search/request/SearchV2Req2.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/search/response/SearchV2Resp2.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);


    }

} 