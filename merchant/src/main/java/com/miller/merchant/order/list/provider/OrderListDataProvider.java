package com.miller.merchant.order.list.provider;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.list.request.OrderListRequestDTO;
import com.miller.merchant.util.RequestUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 订单列表数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 15:44:33
 */
@SuppressWarnings("unused")
public class OrderListDataProvider {
    /**
     * 订单列表数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> orderListOfStatus() {
        // TODO 从数据库中获取数据
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
