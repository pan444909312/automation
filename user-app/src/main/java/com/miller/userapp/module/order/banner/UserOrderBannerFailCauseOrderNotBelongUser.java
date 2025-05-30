package com.miller.userapp.module.order.banner;

import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.dto.order.AfterOrderDetailDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCJ",
        scenarioName = "订单详情-banner列表-订单不属于该用户，提示订单不存在",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/order/banner")
public class UserOrderBannerFailCauseOrderNotBelongUser {

    /**
     * 接口_订单详情-banner列表
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/order/banner";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("订单详情-banner列表-订单不属于该用户，提示订单不存在")
    void testCase(AfterOrderDetailDTO afterOrderDetail) {

        Result resp = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(afterOrderDetail), null, Result.class);


        assertThat(resp.getResultCode()).isEqualTo(5050);
        assertThat(resp.getReason()).isEqualTo("订单不存在");
        assertThat(resp.getError()).isEqualTo("订单不存在");
        assertThat(resp.getResult()).isNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        AfterOrderDetailDTO afterOrderDetail = new AfterOrderDetailDTO();

        afterOrderDetail.setOrderSn("570059526884874758112");

        return Stream.of(Arguments.of(afterOrderDetail));
    }

}
