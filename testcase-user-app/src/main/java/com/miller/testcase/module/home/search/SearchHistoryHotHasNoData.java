package com.miller.testcase.module.home.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


@Scenario(scenarioID = "01K1ZEBC6JD5BR0TE3TN2SNZJR",
        scenarioName = "热搜词-无热搜词",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/search/history/hot")
public class SearchHistoryHotHasNoData {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/search/history/hot";

    @DisplayName("热搜词-无热搜词")
    @Test
    void shouldReturnSuccessfully() {
        List<Map<String, Object>> sqlList = PandaTestDBHelpful.executeSelectListSql("SELECT sh.search_hot_id FROM search_hot sh LEFT JOIN search_hot_link shl on sh.search_hot_id = shl.search_hot_id WHERE sh.city is null and sh.start_time != 0 and shl.city = '全部城市' and sh.status = 1");
        List<Long> idList = sqlList.stream().map(item -> (Long) item.get("search_hot_id")).toList();

        StringBuilder sql = new StringBuilder("UPDATE search_hot set status = 0 WHERE search_hot_id in(");
        StringBuilder sql2 = new StringBuilder("UPDATE search_hot set status = 1 WHERE search_hot_id in(");

        idList.forEach(item -> sql.append(item).append(","));
        idList.forEach(item -> sql2.append(item).append(","));

        sql.deleteCharAt(sql.length()-1).append(")");
        sql2.deleteCharAt(sql2.length()-1).append(")");


        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql.toString());


        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);

        String expectedStr = TestCaseHelpful.getFileContent("module/home/search/response/SearchHistoryHotHasNoDataResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql2.toString());


    }
}
