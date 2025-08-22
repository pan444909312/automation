package com.miller.pandafresh.testcase.module.search.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * search
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/17 19:56:04
 */
@Scenario(
        scenarioID = "01K0C3VPR6T8F1HQ70SVSH28JY", // 自动生成，不要修改
        scenarioName = "搜索-搜索词匹配商品类目名称",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 5, manualTestTime = 3)
@DisplayName("搜索-搜索词匹配商品类目名称")
public class SearchForGoodsCategory_Tests {

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
        String uri = TestcaseConfig.HpfHost + "/search/search";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/search/search/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/search/search/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/search/search/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValue(requestBody,"keyword","青菜");
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);

        // 对json文件中排除的字段进行更加复杂的断言
        JSONArray goodsList = TestCaseHelpful.extractValue(responseBody, "result.records");

        // 收集所有类目名称
        List<String> goodsNames = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {
            Map goods = (Map<String, Object>) goodsList.get(i);
            String goodsName = (String) goods.get("categoryName");
            goodsNames.add(goodsName);
        }

        // 断言数组中包含指定的商品类目
        boolean containsExpectedGoods = goodsNames.contains("青菜");

        TestCaseHelpful.assertThat(containsExpectedGoods).isEqualTo(true);

    }
} 