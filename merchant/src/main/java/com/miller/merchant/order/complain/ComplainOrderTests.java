package com.miller.merchant.order.complain;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.complain.flow.ComplainOrderFlow;
import com.miller.merchant.order.complain.request.ComplainOrderRequestDTO;
import com.miller.merchant.order.complain.response.ComplainOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家崔骑手
 *
 * <p>
 * 此接口在 app-server 代码工程中，无法引用开发的代码
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-催骑手")
public class ComplainOrderTests {

    @MethodSource("complainOrder")
    @ParameterizedTest
    @DisplayName("正常流程_商家催骑手")
    void shouldComplainOrderSuccessfully(ComplainOrderRequestDTO complainOrderRequestDTO) {
        ComplainOrderResponseDTO complainOrderResponseDTO = ComplainOrderFlow.complainOrder(complainOrderRequestDTO);
        assertThat(complainOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(complainOrderResponseDTO.getSuccess()).isTrue();
    }
    static Stream<Arguments> complainOrder() {
        ComplainOrderRequestDTO complainOrderRequestDTO = new ComplainOrderRequestDTO();

        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        complainOrderRequestDTO.setOrderSn(orderSn);
        complainOrderRequestDTO.setComplainType(1);
        return Stream.of(Arguments.of(complainOrderRequestDTO));
    }
}
