package com.miller.testcase.module.business.shopmenu;
import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01JZW3C6SQ4CZGPXXETJRK25KF",
        scenarioName = "进入店铺获取特殊菜单-推荐菜单",
        author = "yaoqianhu@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("进入店铺获取特殊菜单-推荐菜单")
public class recommendMenu {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/app/user/v1/shop/menuList";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/headers.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/home/shop/request/DelivryMenulistReq.json";
    // 断言
    String assert2 = "module/home/shop/response/recommendMenuResp.json";

    @DisplayName("正向流程")
    @Test
    public void getMenuListSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        Object result = TestCaseHelpful.extractValue(responseBody,"$.result.menuList[0].subMenuList[?(@.menuInfo.menuType==16)].menuInfo");
        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
        TestCaseHelpful.assertThat(result.toString()).isNotEqualTo("[]");
    }
}
