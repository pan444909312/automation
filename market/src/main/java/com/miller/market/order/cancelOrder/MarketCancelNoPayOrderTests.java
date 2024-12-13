package com.miller.market.order.cancelOrder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.order.OrderMapper;
import com.miller.market.order.cancelOrder.flow.MarketCancelOrderFlow;
import com.miller.market.order.cancelOrder.request.MarketCancelOrderRequestDTO;
import com.miller.market.order.cancelOrder.response.MarketCancelOrderResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.common.enums.OrderStatusEnum;
import com.panda.market.dal.entity.Order;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 取消订单
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_取消待支付订单")
public class MarketCancelNoPayOrderTests {
    private static OrderMapper orderMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        orderMapper = sqlSession.getMapper(OrderMapper.class);

    }
    @MethodSource("staticNoPayOrderDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_取消待支付订单")
    void cancelNoPayOrderSuccessfully(MarketCancelOrderRequestDTO marketCancelOrderRequestDTO) {
        MarketCancelOrderResponseDTO marketCancelOrderResponseDTO = MarketCancelOrderFlow.cancelOrder(marketCancelOrderRequestDTO);

        Assertions.assertThat(marketCancelOrderResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketCancelOrderResponseDTO.getData()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticNoPayOrderDataProvider() {
        // 查询待支付订单
        MarketCancelOrderRequestDTO requestDTO = new MarketCancelOrderRequestDTO();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("portal_id", BusinessConstant.portalId);
        orderQueryWrapper.eq("user_id", BusinessConstant.userId);
        orderQueryWrapper.eq("order_status", OrderStatusEnum.NOT_PAY.getCode());
        orderQueryWrapper.orderByDesc("order_id");
        orderQueryWrapper.last("limit 1");
        Order order = orderMapper.selectOne(orderQueryWrapper);
        requestDTO.setOrderId(order.getOrderId());
        requestDTO.setOrderSn(order.getOrderSn());

        return Stream.of(Arguments.of(requestDTO));
    }

}
