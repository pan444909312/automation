package com.miller.testcase.factory;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.util.XXLConfUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;


@Scenario(scenarioID = "01J4QYGE34BJ7SP84EBBTWEJPT", scenarioName = "一键自动创建模板商家",
        author = "shandongdong@hungrypandagroup.com",
        developmentTime = 6 * 60, maintenanceTime = 0, manualTestTime = 4 * 60)
@DisplayName("商家工厂_一键自动创建商家")
public class MerchantFactory {
    /**
     * true: 编辑商家；false:创建商家。如果为false则使用指定的 ShopId 对商家进行编辑操作。
     */
    private boolean isEditMerchant = true;
    // 调试ID
    private long shopIdForDebug = 250721460;
    // 商家名称
    private String merchantName = "自动化测试商家";


    public static void main(String[] args) {

    }

    private void createMerchant() {
        MerchantFactory merchantFactory = new MerchantFactory();

        merchantFactory.setUP();

        if (!merchantFactory.isEditMerchant) {
            merchantFactory.step02CreateMerchant(merchantName);
        }
        // 创建九江市。 其他城市需要修改店铺位置、配送范围围栏
        merchantFactory.step03EditMerchantInfoOfBusiness();
        merchantFactory.step04EditMerchantInfoOfCost();
        merchantFactory.step05EditMerchantInfoOfAdditional();
        merchantFactory.step06EditMerchantInfoOfAddKP();
//        merchantFactory.step07CopyOtherShopGoods();
        merchantFactory.step07CreateGoods();
        merchantFactory.step08AddShopBusinessTime();
        merchantFactory.step09AddFence();
        merchantFactory.step10SaveBillInfo();
        merchantFactory.step11MerchantAuth();
        merchantFactory.step12RecommendMerchant();

        merchantFactory.tearDown();
    }

    private void setUP() {
        // 关闭首页店铺流缓存
        XXLConfUtils.updateConfig("test", "user-app-server.shoplist.cache", "【首页店铺流】是否读redis缓存", false);
    }

    /**
     * ERP-商家列表-创建商家
     */
    private String step02CreateMerchant(String merchantName) {
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
    private void step03EditMerchantInfoOfBusiness() {
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
    private void step04EditMerchantInfoOfCost() {
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
    private void step05EditMerchantInfoOfAdditional() {
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
    private void step06EditMerchantInfoOfAddKP() {
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
    private void step07CopyOtherShopGoods() {
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
     * TODO 创建默认菜单，并创建商品
     */
    private void step07CreateGoods() {
        String uri = "https://platform-test-backup.hungrypanda.cn/admin/merchant/recommend.htm";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_add_menu/request/headers.json";
        String params = "factory/merchant_factory/edit_merchant_add_menu/request/params.json";
        String body = null;
        String assertFullField = "factory/merchant_factory/edit_merchant_add_menu/response/assert_full_field.json";
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            requestParams.put("shopId", shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            requestParams.put("shopId", TestCaseHelpful.get("shopId"));
        }
        // https://platform-test-backup.hungrypanda.cn/ 老接口需要用cookie
        String cookie = "CN_isNewFramework=1;CN_token=" + requestHeaders.get("token");
        requestHeaders.put("Cookie", cookie);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, body);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).isEqualTo(expectedStr);
    }


    /**
     * ERP-编辑商家-修改店铺营业时间
     */
    private void step08AddShopBusinessTime() {
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
    private void step09AddFence() {
        /*
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
        cookie = """
                sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218badd9e77310c-047577dbefc4f1-16525634-2073600-18badd9e7741112%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22%24device_id%22%3A%2218badd9e77310c-047577dbefc4f1-16525634-2073600-18badd9e7741112%22%7D; _gcl_au=1.1.637593495.1749693568; CN_isNewFramework=1; SESSION=793eb90a-de9b-452a-bf75-722170c7e8c9; urms_token=Bearer%20eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmNmNmMmZhMDY1OTQ3MGVmOTVhZDJhNmFhMTNiYTdkOSIsIm9yaWdpblVzZXJJZCI6IiIsInVzZXJOaWNrIjoi5Y2V5Lic5LicIiwib3JpZ2luIjoiIiwidXNlclV1aWQiOiJmNmNmMmZhMDY1OTQ3MGVmOTVhZDJhNmFhMTNiYTdkOSIsImlzcyI6ImhwLWlhbS1zZXJ2ZXIiLCJ1c2VyTmFtZSI6IjEwMzkxOSIsImV4cCI6MTc4MTc3NzQ4NCwiaWF0IjoxNzUwMjQxNDg0LCJqdGkiOiIyNTFhNzE0ZTY2ODA0MTE2OWQ2YzUyNGY3YTNhYzdhYSIsImVtYWlsIjoic2hhbmRvbmdkb25nQGh1bmdyeXBhbmRhZ3JvdXAuY29tIn0.jjbDvxJUCNlljt7babU_PIuPTnV5voTzR9RE9S9FFUiKttEc1spfghDy0gwRf6MvnpRPqDxQPAt8dKJBm37AYw; CN_token=426eac48c9dce46cdb7e479ea9971e2b; CN_userInfo=%7B%22userId%22%3A1748%2C%22userToken%22%3A%22426eac48c9dce46cdb7e479ea9971e2b%22%2C%22username%22%3A%22dongdong_test%22%2C%22userNick%22%3A%22%E5%8D%95%E4%B8%9C%E4%B8%9C%22%2C%22userPic%22%3A%22%22%2C%22showManagerBinding%22%3Afalse%2C%22userPhone%22%3A%2215606690056%22%7D

                """;
        requestHeaders.put("Cookie", cookie);

        String encode = URLEncoder.encode(TestCaseHelpful.extractValue(requestBody, "$.areaFenceData[0]").toString(), Charset.defaultCharset());
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.areaFenceData[0]", "%5B%7B%22fenceIdStr%22%3A%222779%22%2C%22pid%22%3A%22398195317%22%2C%22fenceLatlngChain%22%3A%2230.48964%2C119.75968%7C30.25683%2C119.71162%7C30.09299%2C119.77891%7C29.94123%2C119.86714%7C29.88644%2C120.07528%7C29.88879%2C120.28617%7C29.94822%2C120.44015%7C30.07784%2C120.55619%7C30.46449%2C120.55379%7C30.57806%2C120.39140%7C30.63421%2C120.19055%7C30.62652%2C119.95640%22%2C%22fenceName%22%3A%22%E6%9D%AD%E5%B7%9E%E5%B8%82%22%2C%22operate%22%3A3%7D%5D");


        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        */
        String sql;
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            // 设置九江市围栏
            sql = """
                    UPDATE panda_test.hp_shop_delivery_fence t
                    SET t.fence_latlng = '29.80146,115.87807|29.73590,115.81490|29.66313,115.81147|29.60688,115.84271|29.60255,115.91996|29.61807,116.04149|29.65627,116.12767|29.79282,116.08750|29.80772,116.00888|29.81665,115.93609',
                        t.fence_name = '九江市1',
                        t.last_modify_admin = 1748,
                        t.delivery_type = 1,
                        t.fence_template_id = 0
                    WHERE t.shop_id = 
                    """ + shopIdForDebug;
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            // 设置九江市围栏
            sql = " INSERT INTO panda_test.hp_shop_delivery_fence (shop_id, fence_latlng, fence_name, create_time, last_update_time, last_modify_admin, delivery_type, fence_template_id) VALUES ( " +
                    TestCaseHelpful.get("shopId")
                    + ", '29.80146,115.87807|29.73590,115.81490|29.66313,115.81147|29.60688,115.84271|29.60255,115.91996|29.61807,116.04149|29.65627,116.12767|29.79282,116.08750|29.80772,116.00888|29.81665,115.93609', '九江市', DEFAULT, DEFAULT, 1748, 1, 0); ";

        }
        int[] ints = PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
        TestCaseHelpful.assertThatJson(ints).isArray().size().isGreaterThanOrEqualTo(1);
    }

    /**
     * ERP-编辑商家-结算信息
     * ERP-编辑商家-结算信息-佣金
     */
    private void step10SaveBillInfo() {
        // ERP-编辑商家-结算信息
        saveBillInfo("factory/merchant_factory/edit_merchant_save_commission/request/Step10SaveBillInfoOfPanda.json");
        saveBillInfo("factory/merchant_factory/edit_merchant_save_commission/request/Step10SaveBillInfoOfPandaWeb.json");
        saveBillInfo("factory/merchant_factory/edit_merchant_save_commission/request/Step10SaveBillInfoOfPandaGFO.json");

        //  ERP-编辑商家-结算信息-佣金,依赖 ERP-编辑商家-结算信息 先执行
        saveCommission("factory/merchant_factory/edit_merchant_save_commission/request/Step11SaveCommissionOfPanda-1.json");
        saveCommission("factory/merchant_factory/edit_merchant_save_commission/request/Step11SaveCommissionOfPanda-2.json");
        saveCommission("factory/merchant_factory/edit_merchant_save_commission/request/Step11SaveCommissionOfPanda-3.json");
        saveCommission("factory/merchant_factory/edit_merchant_save_commission/request/Step11SaveCommissionOfPanda-4.json");
        saveCommission("factory/merchant_factory/edit_merchant_save_commission/request/Step11SaveCommissionOfPanda-5.json");
        saveCommission("factory/merchant_factory/edit_merchant_save_commission/request/Step11SaveCommissionOfPanda-6.json");
        saveCommission("factory/merchant_factory/edit_merchant_save_commission/request/Step11SaveCommissionOfPandaWeb-1.json");
        saveCommission("factory/merchant_factory/edit_merchant_save_commission/request/Step11SaveCommissionOfGFO-1.json");
    }

    /**
     * 保存结算信息
     *
     * @param fileName 文件名
     */
    private void saveBillInfo(String fileName) {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/merchant/finance/config/saveBillInfo";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_save_commission/request/headers.json";
        String params = null;
        // String body = "factory/merchant_factory/edit_merchant_save_commission/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_save_commission/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestBody = TestCaseHelpful.getJsonRequestBody(fileName);
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
     * 结算信息子项-佣金
     *
     */
    private void saveCommission(String fileName) {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/merchant/finance/config/saveCommission";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_save_commission/request/headers.json";
        String params = null;
        // String body = "factory/merchant_factory/edit_merchant_save_commission/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_save_commission/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestBody = TestCaseHelpful.getJsonRequestBody(fileName);
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
     * ERP-商家管理-商家认证-审核
     */
    private void step11MerchantAuth() {
        String uri = TestcaseConfig.HOST_ERP + "/api/bdm/merchant/auth";
        String method = "POST";
        String headers = "factory/merchant_factory/edit_merchant_auth/request/headers.json";
        String params = null;
        String body = "factory/merchant_factory/edit_merchant_auth/request/body.json";
        String assertFullField = "factory/merchant_factory/edit_merchant_auth/response/assert_full_field.json";

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
     * ERP-编辑商家-推荐商家
     */

    private void step12RecommendMerchant() {
        String uri = "https://platform-test-backup.hungrypanda.cn/admin/merchant/recommend.htm";
        String method = "GET";
        String headers = "factory/merchant_factory/edit_merchant_recommend/request/headers.json";
        String params = "factory/merchant_factory/edit_merchant_recommend/request/params.json";
        String body = null;
        String assertFullField = "factory/merchant_factory/edit_merchant_recommend/response/assert_full_field.json";
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("token", TestCaseHelpful.erpLogin());
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            requestParams.put("shopId", shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            requestParams.put("shopId", TestCaseHelpful.get("shopId"));
        }
        // https://platform-test-backup.hungrypanda.cn/ 老接口需要用cookie
        String cookie = "CN_isNewFramework=1;CN_token=" + requestHeaders.get("token");
        requestHeaders.put("Cookie", cookie);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, body);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).isEqualTo(expectedStr);

    }

    private void tearDown() {
        // 搜索索引更新
        XXLJobUtils.triggerJob("11");
    }


}