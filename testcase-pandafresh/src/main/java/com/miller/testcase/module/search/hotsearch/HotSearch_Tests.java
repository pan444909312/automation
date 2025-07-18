package com.miller.testcase.module.search.hotsearch;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.FreshTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * hotSearch
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/18 10:41:13
 */
@Scenario(
        scenarioID = "01K0DPGEN1DXXTM35N577TWPF0", // 自动生成，不要修改
        scenarioName = "热搜词-返回本站点启用的热搜词",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 25, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("热搜词-返回本站点启用的热搜词")
public class HotSearch_Tests {

    List<String> keywords = new ArrayList<>();

    @BeforeAll
    void beforeAll(){
        //查找宁波站点启用的热搜词
        String sql = "SELECT g.* FROM hot_search g LEFT JOIN hot_search_portal p on g.id=p.hot_search_id WHERE p.portal_id=3 and g.is_del=0 and g.`status`=1 and p.is_del=0;";
        // 查询多条记录
        List<Map<String, Object>> selectListSql = FreshTestDBHelpful.executeSelectListSql(sql);
        for (int i = 0; i < selectListSql.size(); i++) {
            String word = selectListSql.get(i).get("name").toString();
            keywords.add(word);
        }
    }
    @AfterAll
    static void afterAll(){
        // 所有 @Test 方法执行之后会执行  @@AfterAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行后置的操作，比如: 销毁测试数据、还原数据库、清理环境等
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HpfHost + "/search/hotWords";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/search/hotsearch/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/search/hotsearch/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/search/hotsearch/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);

        for (int i = 0; i < keywords.size(); i++) {
            String word = keywords.get(i);

            // 断言返回参数是否包含期望热搜词
            boolean containsExpectedGoods = TestCaseHelpful.extractValue(responseBody, "result.hotWords").toString().contains(word);
            TestCaseHelpful.assertThat(containsExpectedGoods).isEqualTo(true);
        }
    }
} 