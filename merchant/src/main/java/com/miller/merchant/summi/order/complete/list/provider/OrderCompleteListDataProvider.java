package com.miller.merchant.summi.order.complete.list.provider;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.summi.order.complete.list.request.OrderCompleteListRequestDTO;
import com.miller.merchant.util.RequestUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 订单列表数据提供者
 *
 * @author yuwei
 * @version 1.0
 * @since 2024/04/24 15:45:30
 */
@SuppressWarnings("unused")
public class OrderCompleteListDataProvider {
    /**
     * 订单列表数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> orderListOfStatus() {
        // TODO 从数据库中获取数据
        OrderCompleteListRequestDTO orderCompleteListRequestDTO = new OrderCompleteListRequestDTO();
        // 这个请求体里面的tokens是从登录返回的字段获取的
        var tokens = RequestUtils.getHeaders().get(BusinessConstant.authorization).toString();
        orderCompleteListRequestDTO.setTokens(tokens);
        orderCompleteListRequestDTO.setAllLoginToken(tokens);
        /*
         * 订单状态: 5（已完成订单）
         */
        orderCompleteListRequestDTO.setMerchantOrderStatus(5);
        /*
         * 查询可结算订单日期
         */
        orderCompleteListRequestDTO.setQueryDate("2024-04-16");

        return Stream.of(Arguments.of(orderCompleteListRequestDTO));
    }
}
