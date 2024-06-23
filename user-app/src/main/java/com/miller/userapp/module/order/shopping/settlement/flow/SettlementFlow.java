package com.miller.userapp.module.order.shopping.settlement.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_结算页(由于历史原因，也叫创建虚单)
 * 注意: 购物车和结算页面仅仅只是用于计算商品价格、优惠等信息，并不会真正的入库，所以在主流中可以不用加购物车和结算的逻辑，这块可以单独测试。
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 15:15:30
 */
public class SettlementFlow {
    /**
     * 接口_结算页
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v1/order/toCreateVirtual";

    /**
     * 流程_结算
     */
    public static SettlementResponseDTO settlementProduct(SettlementRequestDTO settlementRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(settlementRequestDTO), null, SettlementResponseDTO.class);
    }

}
