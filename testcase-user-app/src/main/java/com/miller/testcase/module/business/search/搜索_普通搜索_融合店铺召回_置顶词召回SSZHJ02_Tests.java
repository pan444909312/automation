package com.miller.testcase.module.business.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 搜索_普通搜索_融合店铺_置顶词
 *
 * @author 张培
 * @version 2.0
 * @since 2025/10/28 14:53:23
 */
@Scenario(
        scenarioID = "01JZQ67ERFWEM68JA3V20M9WMS", // 自动生成，不要修改
        scenarioName = "搜索_普通搜索_融合店铺_置顶词_搜索置顶词：SSZHJ02”",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("搜索_普通搜索_融合店铺_置顶词_搜索置顶词：SSZHJ02")
public class 搜索_普通搜索_融合店铺召回_置顶词召回SSZHJ02_Tests {
    // 待搜索的词
    String searchWord = "置顶词";
    @BeforeAll
    void beforeAll() {
        //查询达达置顶词配置，不包含当前置顶词则获取第一个替换
        String searchWordSql = "SELECT t.config_value FROM panda_test.hp_sys_app_config t where t.config_key = 'DADA_SEARCH_WORDS_PFSEARCH'";
        Map<String, Object> stringObjectMap = PandaTestDBHelpful.executeSelectOneSql(searchWordSql);
        String configValue = stringObjectMap.get("config_value").toString();
        String firstBeforeComma = configValue.split("[,，]")[0].trim();
        searchWord = firstBeforeComma;
    }

    @AfterAll
    void afterAll() {
    }

    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/home/search/chinese/regression_testing/搜索_普通搜索_融合店铺_商品/request/headers02.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/home/search/chinese/regression_testing/搜索_普通搜索_融合店铺_商品/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/home/search/chinese/regression_testing/搜索_普通搜索_融合店铺_商品/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.keywords",searchWord);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        // 对json文件中排除的字段进行更加复杂的断言
        // 断言店铺列表中包含指定店铺
        JSONArray shopList = TestCaseHelpful.extractValue(responseBody, "result.shopList");
        // 置顶店铺是否有达达店铺标志
         Map shop = (Map<Integer, Object>) shopList.get(0);
         Integer isSupermarket = (Integer) shop.get("isSupermarket");

        // 断言数组中包含达达店铺
        boolean containIsSupermarket = isSupermarket.toString().contains("1");

        TestCaseHelpful.assertThat(containIsSupermarket).isEqualTo(true);


    }

} 