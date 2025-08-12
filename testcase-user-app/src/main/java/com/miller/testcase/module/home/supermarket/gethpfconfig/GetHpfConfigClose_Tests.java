package com.miller.testcase.module.home.supermarket.gethpfconfig;

import com.miller.service.dto.XXLConfigEnvEnum;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.util.XXLConfUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * getHpfConfig
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/10 20:40:17
 */
@Scenario(
        scenarioID = "01JZT5KMX0W6RQP1GQVHY0RD15", // 自动生成，不要修改
        scenarioName = "pf融合开关：开启&定位pf运营区域外",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("pf融合开关：开启&定位pf运营区域外")
public class GetHpfConfigClose_Tests {

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        //关闭融合开关
//        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", false);
//        Thread.sleep(3000L);
    }
    @AfterAll
    static void afterAll(){
        //开启融合开关
//        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", true);
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HOST_APP + "/api/user/supermarket/getHpfConfig";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "GET";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/home/supermarket/gethpfconfig/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = null;
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/home/supermarket/gethpfconfig/response/assert_full_fieldClose.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.replace("latitude","59.50722");
        requestHeaders.replace("longitude","-0.12759");

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