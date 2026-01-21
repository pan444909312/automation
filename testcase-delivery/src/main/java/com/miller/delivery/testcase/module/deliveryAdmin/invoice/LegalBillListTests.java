package com.miller.delivery.testcase.module.deliveryAdmin.invoice;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 法务账单-列表
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT2096FJ7Y9BE380ABSVPHJ",
        scenarioName = "法务账单-列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("法务账单-列表")
public class LegalBillListTests {

    @DisplayName("法务账单列表-城市")
    @Test
    void shouldGetLegalBillList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算上周一和上周日
        LocalDate today = LocalDate.now();
        LocalDate lastMonday = getLastWeekMonday(today);
        LocalDate lastSunday = getLastWeekSunday(lastMonday);
        String lastWeekStart = lastMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String lastWeekEnd = lastSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 法务账单列表-城市
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/act/data/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format(
                "{\"date\":[\"%s\",\"%s\"],\"pageNo\":1,\"pageSize\":10,\"city\":\"杭州市\",\"dateStart\":\"%s\",\"dateEnd\":\"%s\"}",
                lastWeekStart, lastWeekEnd, lastWeekStart, lastWeekEnd);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 验证返回数据不为空
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    /**
     * 获取上周一
     */
    private LocalDate getLastWeekMonday(LocalDate today) {
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int daysToLastMonday = dayOfWeek == DayOfWeek.SUNDAY ? 6 : dayOfWeek.getValue() - 1 + 7;
        return today.minusDays(daysToLastMonday);
    }

    /**
     * 获取上周日（上周一 + 6天）
     */
    private LocalDate getLastWeekSunday(LocalDate lastMonday) {
        return lastMonday.plusDays(6);
    }

    /**
     * 创建请求头
     */
    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

