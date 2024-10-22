package com.miller.market.specialOrder.getPageList.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.dto.BasicResponseDTO;
import com.panda.market.dal.vo.order.SpecialOrderVO;


/**
 * 特殊单订单列表响应对象
 */
public class MarketSpecialOrderListResponseDTO extends BasicResponseDTO<Page<SpecialOrderVO>> {

}
