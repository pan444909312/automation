package com.miller.testcase.module.account.address;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/29 15:19:42
 */
@Scenario(
        scenarioID = "01K1Z8E5SZ1WZK7D92VPWCM2GN",
        scenarioName = "用户_删除地址",
        author = "panjuxiang@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("用户_删除地址")
public class DeleteAddressTests {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/app/user/v1/address/edit";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/account/address/request/headers.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/account/address/request/AddressDeleteReq.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 paras 参数
    String params = null;
    // 断言
    String assert1 = "module/account/address/response/AddressDeleteResp.json";

    @DisplayName("正向流程")
    @Test
    void shouldLoginSuccessfully() {
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("INSERT INTO `panda_test`.`address` ( `add_conn_name`, `add_conn_sex`, `add_conn_tel`, `add_province`, `add_city`, `add_street`, `add_location`, `add_house_num`, `add_postcode`, `add_longitude`, `add_latitude`, `create_time`, `update_time`, `is_del`, `is_defult`, `add_country`, `temp_uid`, `temp_add_id`, `country_code`, `delivery_note`, `first_name`, `last_name`, `address_remark`, `user_id`, `effective_postal_code`, `add_tag`, `add_source`, `last_order_modify_time`, `building_name`, `deliverable_action`, `deliverable_remark`, `add_conn_tel_mask`, `geo_longitude`, `geo_latitude`, `geo_location_type`, `platform_source`, `version`, `role_id`, `building_type`, `access_code`, `building_name_ext`, `guide_image`) VALUES ( '自动化测试', '1', '139hrjg0008', '', '九江市', '', 'China, Jiangxi, 九江', '测试', '000000', '115.9541', '29.6605799', 1754470728753, 1754470728753, 0, 0, '中国', NULL, NULL, '86', '', '', '', '', 1398718111, '332000', 1, 0, 0, '九江', 2, '', '139hrjg0008', '115.9541', '29.6605799', 1, '', '', 0, 1, '123456', '测试', '');");
        Map<String, Object> map = PandaTestDBHelpful.executeSelectOneSql("SELECT add_id from address WHERE user_id = 1398718111 order by add_id desc limit 1");
        Long add_id = (Long) map.get("add_id");
        System.out.println("add_id:" + add_id);

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        // 给请求头添加数据，例如这里添加token
        requestHeaders.put("Authorization", TestCaseHelpful.login("13999900008", "123456"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        String requestBody = TestCaseHelpful.getJsonRequestBody(body);

        String newRequestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.addressId", add_id);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, newRequestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert1);

        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from address WHERE add_id = " + add_id);



    }
}
