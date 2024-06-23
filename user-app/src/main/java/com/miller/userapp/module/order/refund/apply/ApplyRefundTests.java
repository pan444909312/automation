package com.miller.userapp.module.order.refund.apply;

import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.refund.apply.response.ApplyRefundResponseDTO;
import com.miller.userapp.module.order.refund.apply.flow.ApplyRefundFlow;
import com.miller.userapp.module.order.refund.apply.request.ApplyRefundRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_申请退款
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/29 11:27:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-申请退款")
public class ApplyRefundTests {

    @MethodSource("applyRefund")
    @ParameterizedTest
    @DisplayName("正常流程_申请退款")
    void shouldApplyRefundSuccessfully(ApplyRefundRequestDTO applyRefundRequestDTO) {
        ApplyRefundResponseDTO applyRefundResponseDTO = ApplyRefundFlow.applyRefund(applyRefundRequestDTO);
        assertThat(applyRefundResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(applyRefundResponseDTO.getSuccess()).isTrue();
        assertThat(applyRefundResponseDTO.getResult().getOrderSn()).isNotNull();
    }
    static Stream<Arguments> applyRefund() {
        ApplyRefundRequestDTO applyRefundRequestDTOByDelivery = new ApplyRefundRequestDTO();
        applyRefundRequestDTOByDelivery.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        applyRefundRequestDTOByDelivery.setOrderSn(orderSn);

        return Stream.of(Arguments.of(applyRefundRequestDTOByDelivery));
    }
}
