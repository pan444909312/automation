package com.miller.testcase.module.business.shopmenu.pickup;
import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01KBPYQ0P4A7S8BTBRXPDF0B3T",
        scenarioName = "自取列表-商卡信息-商品标签：门店销量第x名",
        author = "yaoqianhu@hungrypandagroup.com",
        developmentTime = 45, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("自取列表-商卡信息-商品标签：门店销量第x名")
public class product_门店销量第x名 {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/app/user/v1/shop/menuList";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/home/shop/request/headers_new.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/home/shop/request_pickup/DelivryMenulistReq.json";
    // 断言
    String assert2 = "module/home/shop/response/product_info.json";

    String assert1 = "module/home/shop/response/product_门店销量第x名.json";
    @DisplayName("正向流程")
    @Test
    public void getMenuListSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 直接提取productId=82539065商品中tagType=1的标签
        Object result = TestCaseHelpful.extractValue(responseBody,"$.result.menuList[0].subMenuList[*].productList[?(@.productId==82539065)].systemMarketingTags[*][?(@.tagType==1)]");

        var expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
        TestCaseHelpful.assertThat(result.toString()).isNotEqualTo("[]");

        var expectedStr2 = TestCaseHelpful.getFileContent(assert1);
        TestCaseHelpful.assertThatJson(result).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr2);


    }
}
