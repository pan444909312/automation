package com.miller.testcase.module.index.getindex;

import com.miller.service.dto.XXLConfigEnvEnum;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.util.XXLConfUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * getIndex
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/18 15:28:58
 */
@Scenario(
        scenarioID = "01K0E6ZB3Z1N0FXPJR0RVHKW43", // 自动生成，不要修改
        scenarioName = "pf首页-商品瀑布流-猜你喜欢开关关闭",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("pf首页-商品瀑布流-猜你喜欢开关关闭")
public class GetIndexRecommendOff_Tests {

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        //开启猜你喜欢
        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "hp-market-api.goods.recommend.enable", "是否开启商品推荐", false);
        Thread.sleep(3000L);
    }
    @AfterAll
    static void afterAll(){
        //开启猜你喜欢
        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "hp-market-api.goods.recommend.enable", "是否开启商品推荐", true);
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HpfHost + "/index/getIndex";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/index/getindex/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/index/getindex/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/index/getindex/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.indexList[?(@.type=='goods_flow')].items.[0].type").isNotEqualTo("[0]");

    }
} 