package com.miller.testcase.module.home.search.chinese.regression_testing.搜索_精准搜索_未全部打烊_无广告;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.factory.MerchantFactory;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 搜索 - 精准搜索&未全部打烊&无广告
 *
 * @author 单东东
 * @version 2.0
 * @since 2025/06/22 17:23:22
 */
@Scenario(
        scenarioID = "01JYBF653F45C8MRHHEB8W27QH", // 自动生成，不要修改
        scenarioName = "搜索-精准搜索&未全部打烊&无广告",
        author = "shandongdong@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("搜索-精准搜索&未全部打烊&无广告")
public class 搜索_精准搜索_未全部打烊_无广告_Tests {

    @BeforeAll
    void beforeAll(){
        /**
         * 1. hp_data_search_entity_word.word_type = 1
         * 2. hp_data_search_entity_word.task_id 当前最大的
         * 3. 仅shopId = 3333、4444、5555 店名包含搜索词“喜茶”
         * 4. shopId = 3333 打烊，4444、5555未打烊，且有效：未删除、已审核、普通店铺或美食城、站点、语言等
         * 5. 无广告
         * 6. 搜索定时任务：实体词更新 已执行
         */
//        String searchWord = "喜茶";
//        String sql = "INSERT INTO hp_data_search_entity_word (search_word, word_type, gmt_created, task_id, version) VALUES ('" + searchWord + "', 1, DEFAULT, 20250514, 1)";
        // 插入搜索词
//        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
        MerchantFactory.quickCreateMerchant(MerchantFactory.City.JIUJIANG, "喜茶");
        MerchantFactory.quickCreateMerchant(MerchantFactory.City.JIUJIANG, "中间包含喜茶的店");
        MerchantFactory.quickCreateMerchant(MerchantFactory.City.JIUJIANG, "喜茶开头的店");

        // 执行定时任务,：实体词更新
//        XXLJobUtils.triggerJob("11");
        // 搜索索引更新
//        XXLJobUtils.triggerJob("298");
    }
    @AfterAll
    void afterAll(){
        // 删除 hp_data_search_entity_word 表插入的数据 品牌词 记录
        // 删除 店铺
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/home/search/chinese/regression_testing/搜索_精准搜索_未全部打烊_无广告/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/home/search/chinese/regression_testing/搜索_精准搜索_未全部打烊_无广告/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/home/search/chinese/regression_testing/搜索_精准搜索_未全部打烊_无广告/response/assert_full_field.json";

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

    }
} 