package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 编辑笔记
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP877ADBDXY30PT9DYKYE4J",
        scenarioName = "骑手app-地图笔记-编辑笔记",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("编辑笔记")
public class EditNoteTests {

    // 注意：需要在实际使用时替换为真实的noteId
    private static final Long NOTE_ID = 1L; // 请从质量平台或实际业务中获取

    @DisplayName("编辑笔记")
    @Test
    void shouldEditNote() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 编辑笔记
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/note/updateMapNote";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format(
                "{\"images\":\"\",\"areaId\":58,\"latitude\":\"30.20331960000001\"," +
                "\"anchorPointAddress\":\"中华人民共和国浙江省杭州市中国浙江省杭州市滨江区江陵路1740号310051杭州骏宝行汽车销售服务有限公司\"," +
                "\"noteId\":%d,\"items\":{\"markOptionList\":[{\"code\":0,\"genre\":0,\"name\":\"出入口\"," +
                "\"optionResp\":{\"markOptionList\":[{\"code\":0,\"genre\":0,\"name\":\"步行\"," +
                "\"key\":\"MAP_NOTE_ENTRANCE_SUB_TYPE_WALKING\"}]},\"key\":\"MAP_NOTE_LOCATION_TYPE_ENTRANCE\"}]}," +
                "\"longitude\":\"120.21682040000002\",\"noteStatus\":1}",
                NOTE_ID);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("longitude", "120.2168232");
        headers.put("latitude", "30.2033218");
        headers.put("version", "5.59.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("user-agent", "5.59.0");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "samsung");
        headers.put("uniquetoken", "34ea70ca94766bbc");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

