package com.miller.testcase.factory;

import com.miller.service.util.XXLConfUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;

/**
 * 创建商家的数据工厂类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/27 23:04:49
 */
public class MerchantFactory {
    /**
     * true: 编辑商家；false:创建商家。如果为false则使用指定的 ShopId 对商家进行编辑操作。
     */
    private static boolean isEditMerchant = true;
    private static long shopIdForDebug = 250721460;
    // 商家名称
    private static String merchantName = "自动化测试商家";

    public static void main(String[] args) {
        MerchantFactory merchantFactory = new MerchantFactory();
        if (!isEditMerchant) {
            merchantFactory.step02CreateMerchant();
        }

    }

    private void step01ERPLogin() {
        // 关闭首页店铺流缓存
        XXLConfUtils.updateConfig("test", "user-app-server.shoplist.cache", "【首页店铺流】是否读redis缓存", false);

    }

    /**
     * 创建商家
     */
    private String step02CreateMerchant() {
        if (merchantName.isBlank()) {
            throw new RuntimeException("请指定商家名称");
        }
        /* 名称添加时间戳的后6位，避免重复 */
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/module/save";
        String method = "POST";
        String headers = "factory/merchant_factory/create_merchant/request/headers.json";
        String params = null;
        String body = "factory/merchant_factory/create_merchant/request/body.json";
        String assertFullField = "factory/merchant_factory/create_merchant/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 商家名称
        requestBody = TestCaseHelpful.updateJsonValue(requestBody,
                "$.baseInfo.operationNameList[?(@.lang=='CN')].sortList[?(@.operationName=='NAME')].value", merchantName);
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        // 创建商家失败
        if (!TestCaseHelpful.extractValue(responseBody, "code").toString().equalsIgnoreCase("1")) {
            throw new RuntimeException("创建商家失败" + TestCaseHelpful.extractValue(responseBody, "message"));
            // return TestCaseHelpful.extractValue(responseBody, "message");
        }
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        return responseBody;
    }
}