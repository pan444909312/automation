package com.miller.testcase.factory;

import com.miller.service.util.XXLConfUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
        // 创建九江市。 其他城市需要修改店铺位置、配送范围围栏
        merchantFactory.step03EditMerchantInfoOfBusiness();
        merchantFactory.step04EditMerchantInfoOfCost();
        merchantFactory.step05EditMerchantInfoOfAdditional();
        merchantFactory.step06EditMerchantInfoOfAddKP();
        merchantFactory.step07CopyOtherShopGoods();
        merchantFactory.step08AddShopBusinessTime();
        merchantFactory.step09AddFence();

    }

    private void step01Init() {
        // 关闭首页店铺流缓存
        XXLConfUtils.updateConfig("test", "user-app-server.shoplist.cache", "【首页店铺流】是否读redis缓存", false);

    }

    /**
     * ERP-商家列表-创建商家
     */
    private String step02CreateMerchant() {
        if (merchantName.isBlank()) {
            throw new RuntimeException("请指定商家名称");
        }
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
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.baseInfo.operationNameList[?(@.lang=='CN')].sortList[?(@.operationName=='NAME')].value", merchantName);
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        // 创建商家失败
        if (!TestCaseHelpful.extractValue(responseBody, "code").toString().equalsIgnoreCase("1")) {
            throw new RuntimeException("创建商家失败" + TestCaseHelpful.extractValue(responseBody, "message"));
            // return TestCaseHelpful.extractValue(responseBody, "message");
        }
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        TestCaseHelpful.set("shopId", TestCaseHelpful.extractValue(responseBody, "data.shopId"));
        return responseBody;
    }


    /**
     * ERP-编辑商家-经营信息
     */
    public void step03EditMerchantInfoOfBusiness() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/module/business/info/edit";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_business/request/headers.json";
        String params = null;
        String body = "factory/merchant_factory/edit_merchant_business/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_business/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", TestCaseHelpful.get("shopId"));
        }
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }

    /**
     * ERP-编辑商家-费用配置
     *
     */
    public void step04EditMerchantInfoOfCost() {
        // 使用默认值，暂不需要编辑费用配置
        // 配送平台服务费
        // 自取平台服务费
        // 塑料袋打包费
        // 配送小额订单费
        // 自取小额订单费
    }

    /**
     * ERP-编辑商家-补充信息
     */
    public void step05EditMerchantInfoOfAdditional() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/module/additional/edit";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_additional/request/headers.json";
        String params = null;
        String body = "factory/merchant_factory/edit_merchant_additional/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_additional/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", TestCaseHelpful.get("shopId"));
        }
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }

    /**
     * ERP-编辑商家-KP信息
     */
    public void step06EditMerchantInfoOfAddKP() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/module/kp/save";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_add_kp/request/headers.json";
        String params = null;
        String body = "factory/merchant_factory/edit_merchant_add_kp/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_add_kp/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", shopIdForDebug);
            // kp 不需要通过自动化编辑
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", TestCaseHelpful.get("shopId"));
            var requestParams = TestCaseHelpful.getJsonRequestParams(params);
            var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
            var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
            TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        }
    }

    /**
     * ERP-编辑商家-复制其他店铺商品
     */
    public void step07CopyOtherShopGoods() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/product/copyOtherShopProduct";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_copy_goods/request/headers.json";
        String params = null;
        String body = "factory/merchant_factory/edit_merchant_copy_goods/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_copy_goods/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", TestCaseHelpful.get("shopId"));
        }
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }

    /**
     * ERP-编辑商家-修改店铺营业时间
     */
    public void step08AddShopBusinessTime() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/save/shop/business-time";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_business_time/request/headers.json";
        String params = null;
        String body = "factory/merchant_factory/edit_merchant_business_time/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_business_time/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.shopId", TestCaseHelpful.get("shopId"));
        }
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }

    /**
     * ERP-编辑商家-配送围栏
     */
    public void step09AddFence() {
        // 注意：erp 老项目特殊处理，需要添加 cookie
        String uri = "https://platform-test-backup.hungrypanda.cn/admin/merchant/fence/save/fenceLatlngChain.htm";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_add_fence/request/headers.json";
        String params = null;
        String body = "factory/merchant_factory/edit_merchant_add_fence/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_add_fence/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.areaFenceData[0].pid", shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.areaFenceData[0].pid", TestCaseHelpful.get("shopId"));
        }
        String cookie = "CN_isNewFramework=1;CN_token=" + requestHeaders.get("token");
        requestHeaders.put("Cookie", cookie);

        try {
            String encode = URLEncoder.encode(TestCaseHelpful.extractValue(requestBody, "$.areaFenceData[0]").toString(), "UTF-8");
            requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.areaFenceData[0]", encode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }

    /**
     * ERP-编辑商家-结算信息
     * ERP-编辑商家-结算信息-佣金
     */

//    public void step10SaveBillInfo() {
//        // ERP-编辑商家-结算信息
//        saveBillInfo("Step10SaveBillInfoOfPanda.json");
//        saveBillInfo("Step10SaveBillInfoOfPandaWeb.json");
//        saveBillInfo("Step10SaveBillInfoOfPandaGFO.json");
//        //  ERP-编辑商家-结算信息-佣金,依赖 ERP-编辑商家-结算信息 先执行
//        saveCommission("Step11SaveCommissionOfPanda-1.json");
//        saveCommission("Step11SaveCommissionOfPanda-2.json");
//        saveCommission("Step11SaveCommissionOfPanda-3.json");
//        saveCommission("Step11SaveCommissionOfPanda-4.json");
//        saveCommission("Step11SaveCommissionOfPanda-5.json");
//        saveCommission("Step11SaveCommissionOfPanda-6.json");
//        saveCommission("Step11SaveCommissionOfPandaWeb-1.json");
//        saveCommission("Step11SaveCommissionOfGFO-1.json");
//    }
//
}