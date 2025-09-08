package com.miller.testcase.module.business.shopmenu;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * product_店内复购第x名
 *
 * @author yaoqianhu
 * @version 2.0
 * @since 2025/09/05 13:45:43
 */
@Scenario(
        scenarioID = "01K4CQ1ZYQS94XZYYPH0ZFSCZH", // 自动生成，不要修改
        scenarioName = "商卡-商品信息-商品标签：买过x次",
        author = "yaoqianhu@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("商卡-商品信息-商品标签：买过x次")
public class product_买过x次 {

    String uri = TestcaseConfig.HOST_APP + "/api/app/user/v1/shop/menuList";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/home/shop/request/headers_new.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/home/shop/request/DelivryMenulistReq.json";
    // 断言
    String assert2 = "module/home/shop/response/product_info.json";

    String assert1 = "module/home/shop/response/product_买过x次.json";
    @DisplayName("正向流程")
    @Test
    public void getMenuListSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("Authorization", TestCaseHelpful.login("13711111111", "111111"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 直接提取productId=82539065商品中tagType=1的标签
        Object result = TestCaseHelpful.extractValue(responseBody,"$.result.menuList[0].subMenuList[*].productList[?(@.productId==82539065)].systemMarketingTags[*][?(@.tagType==3)]");

        var expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
        TestCaseHelpful.assertThat(result.toString()).isNotEqualTo("[]");

        var expectedStr2 = TestCaseHelpful.getFileContent(assert1);
        TestCaseHelpful.assertThatJson(result).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr2);


    }
}
