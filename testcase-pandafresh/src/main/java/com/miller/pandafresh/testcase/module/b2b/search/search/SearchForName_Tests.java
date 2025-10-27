package com.miller.pandafresh.testcase.module.b2b.search.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * search
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/23 15:36:01
 */
@Scenario(
        scenarioID = "01K87ZXZEXH7XJZ72PP3CYWYWC", // 自动生成，不要修改
        scenarioName = "b2b搜索:匹配商品名称",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("b2b搜索:匹配商品名称")
public class SearchForName_Tests {

    @BeforeAll
    static void beforeAll(){
        // 所有 @Test 方法执行之前会执行  @BeforeAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行前置的操作，比如: SQL 初始化用例的前置条件
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
        String uri = TestcaseConfig.H5HOST + "/search/b2b/search";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/b2b/search/search/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/b2b/search/search/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/b2b/search/search/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        //登录用户
        requestHeaders.put("authorization",TestCaseHelpful.loginB2B("17700004444","888888"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.pd.keyword","B2B");
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1);
        // 对json文件中排除的字段进行更加复杂的断言
        JSONArray goodsList = TestCaseHelpful.extractValue(responseBody, "data.records");
        // 收集所有商品名称
        java.util.Set<String> goodsNames = new java.util.HashSet<>();
        for (int i = 0; i < goodsList.size(); i++) {
            Map goods = (Map<String, Object>) goodsList.get(i);
            String goodsName = (String) goods.get("goodsName");
            goodsNames.add(goodsName);
        }

        // 断言数组中包含指定的商品名称
        boolean containsExpectedGoods = goodsNames.stream().anyMatch(name -> name.contains("B2B"));

        TestCaseHelpful.assertThat(containsExpectedGoods).isEqualTo(true);

    }
} 