package com.miller.userapp.module.order.banner;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.res.order.OrderInfoVO;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.dto.order.AfterOrderDetailDTO;
import com.hungrypanda.app.server.vo.order.OrderBannerVO;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCG",
        scenarioName = "订单详情-banner列表-展示成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/order/banner")
public class UserOrderBannerSuccess {

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
    @DisplayName("订单详情-banner列表-展示成功")
    void testCase(AfterOrderDetailDTO afterOrderDetail) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(afterOrderDetail), null, Result.class);
        OrderBannerVO orderBannerVO = JSON.parseObject(result.getResult().toString(), OrderBannerVO.class);

        OrderInfoVO.Banner banner = orderBannerVO.getBanners().stream().filter(item -> item.getAdId().equals(363L)).findFirst().orElse(null);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(banner).isNotNull();
        // 跳转类型为会员的校验
        assertThat(banner.getBannerType()).isEqualTo(6);
        assertThat(banner.getBannerLinkUrl()).isEqualTo("hptakeout://module=deeplink&action=openPage&params=eyJ1cmwiOiJocHRha2VvdXQ6Ly9ub25NZW1iZXIifQ**&callback=CALLBACK_NAME&code=c4a3c1b9e2a490a");


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        AfterOrderDetailDTO afterOrderDetail = new AfterOrderDetailDTO();

//        afterOrderDetail.setOrderSn("570059526884874758112");
        afterOrderDetail.setOrderSn("3750488181442725081653");

        return Stream.of(Arguments.of(afterOrderDetail));
    }

}
