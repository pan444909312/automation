package com.miller.merchant.order.delivery;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.delivery.flow.MerchantConfirmUserReceivedOrderFlow;
import com.miller.merchant.order.delivery.request.MerchantConfirmUserReceivedOrderRequestDTO;
import com.miller.merchant.order.delivery.response.MerchantConfirmUserReceivedOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_配送中列表-商家点击"用户已取餐"
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 20:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-配送中列表-商家点击用户已取餐")
public class MerchantConfirmUserReceivedOrderTests {
    @MethodSource("com.miller.merchant.order.delivery.provider.MerchantConfirmUserReceivedOrderDataProvider#merchantConfirmUserReceivedOrder")
    @ParameterizedTest
    @DisplayName("正常流程_配送中列表-商家点击用户已取餐")
    void shouldConfirmUserReceivedOSuccessfully(MerchantConfirmUserReceivedOrderRequestDTO merchantConfirmUserReceivedOrderRequestDTO) {
        MerchantConfirmUserReceivedOrderResponseDTO merchantConfirmUserReceivedOrderResponseDTO =
                MerchantConfirmUserReceivedOrderFlow.merchantConfirmUserReceivedOrderFlow(merchantConfirmUserReceivedOrderRequestDTO);
        assertThat(merchantConfirmUserReceivedOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(merchantConfirmUserReceivedOrderResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
}
