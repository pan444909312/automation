package com.miller.market.order.orderList.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.dto.BasicResponseDTO;
import com.panda.market.dal.dto.OrderDTO;


/**
 * 订单列表响应对象
 */
public class MarketOrderListlResponseDTO extends BasicResponseDTO<Page<OrderDTO>> {

}
