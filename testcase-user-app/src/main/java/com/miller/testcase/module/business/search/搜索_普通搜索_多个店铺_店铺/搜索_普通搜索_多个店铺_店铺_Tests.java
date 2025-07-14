package com.miller.testcase.module.business.search.搜索_普通搜索_多个店铺_店铺;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.util.XXLJobUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.factory.MerchantFactory;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;

/**
 * 搜索_普通搜索_多个店铺_店铺
 *
 * @author 单东东
 * @version 2.0
 * @since 2025/07/09 16:53:23
 */
@Scenario(
        scenarioID = "01JZQ67ERFWEM68JA3V20M9WMT", // 自动生成，不要修改
        scenarioName = "搜索_普通搜索_多个店铺_店铺",
        author = "shandongdong@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("搜索_普通搜索_多个店铺_店铺")
public class 搜索_普通搜索_多个店铺_店铺_Tests {
    // 待搜索的词
    String searchWord = "隔壁";
    @BeforeAll
    void beforeAll() {
        var searchWordSql = "SELECT t.search_word FROM panda_test.hp_data_search_entity_word t where t.search_word = '"
                + searchWord + "'";
        Map<String, Object> stringObjectMap = PandaTestDBHelpful.executeSelectOneSql(searchWordSql);
        // 如果不存在需要的数据，则插入
        if (Objects.isNull(stringObjectMap)) {
            //  查找最大的task_id
            var maxTaskIdSql = """
                    -- 查找最大task_id
                    SELECT max(task_id)
                    from panda_test.hp_data_search_entity_word t
                    """;
            Object taskId = PandaTestDBHelpful.executeSelectOneSql(maxTaskIdSql).get("max(task_id)");

            String insertSearchWordSql = "INSERT INTO panda_test.hp_data_search_entity_word (search_word, word_type, gmt_created, task_id, version) " +
                    "VALUES ('" + searchWord + "', 1, DEFAULT, " + taskId + ", 1)";
            PandaTestDBHelpful.executeInsertOrUpdateOrDelete(insertSearchWordSql);
        }
        // 创建两个商家，已经创建了，这里不重复创建
//         MerchantFactory.quickCreateMerchant(MerchantFactory.City.JIUJIANG, "隔壁");
//         MerchantFactory.quickCreateMerchant(MerchantFactory.City.JIUJIANG, "中间包含隔壁");

        // 执行定时任务：实体词更新
//         XXLJobUtils.triggerJob("11");
        // 执行定时任务：搜索索引更新
//        XXLJobUtils.triggerJob("298");
    }

    @AfterAll
    void afterAll() {
        // 删除 hp_data_search_entity_word 表插入的数据 品牌词 记录
        // String sql = "DELETE FROM panda_test.hp_data_search_entity_word WHERE search_word = '" + searchWord + "';";
        // PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
        // 删除 店铺，先打烊
        // MerchantFactory.closedMerchant("CoCo");
        // MerchantFactory.deleteMerchant("CoCo");
        // MerchantFactory.closedMerchant("中间包含coCo");
        // MerchantFactory.deleteMerchant("中间包含coCo");
    }

    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/home/search/chinese/regression_testing/搜索_普通搜索_多个店铺_店铺/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/home/search/chinese/regression_testing/搜索_普通搜索_多个店铺_店铺/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/home/search/chinese/regression_testing/搜索_普通搜索_多个店铺_店铺/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
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
        // 收集所有店铺名称
        java.util.Set<String> shopNames = new java.util.HashSet<>();
        for (int i = 0; i < shopList.size(); i++) {
            Map shop = (java.util.Map<String, Object>) shopList.get(i);
            String shopName = (String) shop.get("shopName");
            shopNames.add(shopName);
        }

        // 断言数组中包含指定的店铺名称
        boolean containsExpectedShop1 = shopNames.stream().anyMatch(name -> name.contains("隔壁"));
        boolean containsExpectedShop2 = shopNames.stream().anyMatch(name -> name.contains("中间包含隔壁"));

        TestCaseHelpful.assertThat(containsExpectedShop1).isEqualTo(true);
        TestCaseHelpful.assertThat(containsExpectedShop2).isEqualTo(true);

    }

} 