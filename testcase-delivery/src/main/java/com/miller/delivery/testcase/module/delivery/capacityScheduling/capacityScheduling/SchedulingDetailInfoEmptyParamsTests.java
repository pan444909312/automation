package com.miller.delivery.testcase.module.delivery.capacityScheduling.capacityScheduling;

import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 预约排班-详情--参数为空
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01KE6202933358MS7HYTM9QBKD", // 自动生成，不要修改
        scenarioName = "预约排班-详情--参数为空",
        author = "panjuxiang@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("预约排班-详情--参数为空")
public class SchedulingDetailInfoEmptyParamsTests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = "https://app-deliverytest.hungrypanda.cn/api/delivery/app/capacityScheduling/schedulingDetailInfo";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/delivery/capacityScheduling/response/assert_empty_params.json";

    @DisplayName("参数为空")
    @Test
    void shouldFailWithEmptyParams() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/delivery/capacityScheduling/request/headers.json");
        // 设置请求头参数
        headers.put("authorization", TestCaseHelpful.deliveryLogin( "13300010015", "Test1234")); // 需要先登录获取token
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("apptypeid", "2");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        // 获取当天日期 yyyy-MM-DD
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/delivery/capacityScheduling/request/body.json");
        // 更新请求体中的动态参数 - 参数为空的情况
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.schedulingType", 3);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.areaId", 51);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.schedulingDate", "nowDate");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.startTime", "");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.endTime", "");

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        // 验证关键字段 - 参数错误的情况
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }
}

