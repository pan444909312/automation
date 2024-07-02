package com.miller.merchant.order.list;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.list.flow.OrderListFlow;
import com.miller.merchant.order.list.request.OrderListRequestDTO;
import com.miller.merchant.order.list.response.OrderListResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * 测试用例_订单列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 18:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-订单列表")
public class OrderListTests {
    @MethodSource("orderListOfStatus")
    @ParameterizedTest
    @DisplayName("正常流程_订单列表")
    void shouldGetOrderListSuccessfully(OrderListRequestDTO orderListRequestDTO) {
        OrderListResponseDTO orderListResponseDTO = OrderListFlow.orderList(orderListRequestDTO);
        Assertions.assertThat(orderListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        Assertions.assertThat(orderListResponseDTO.getSuccess()).isTrue();
    }
    /**
     * 订单列表数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> orderListOfStatus() {
        OrderListRequestDTO orderListRequestDTO = new OrderListRequestDTO();
        // 这个请求体里面的tokens是从登录返回的字段获取的
        var tokens = RequestUtils.getHeaders().get(BusinessConstant.authorization).toString();
        orderListRequestDTO.setTokens(tokens);
        /*
         * 订单状态: 1(待接单列表); 2(备餐中列表); 3(待取餐列表); 4(配送中列表)
         */
        orderListRequestDTO.setMerchantOrderStatus(1);
        return Stream.of(Arguments.of(orderListRequestDTO));
    }
}
