package com.miller.userapp.module.order.shopping.settlement.flow;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程_结算页(由于历史原因，也叫创建虚单)
 * 注意: 购物车和结算页面仅仅只是用于计算商品价格、优惠等信息，并不会真正的入库，所以在主流中可以不用加购物车和结算的逻辑，这块可以单独测试。
 *
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

    /**
     * 查询结算页订单总金额，结算页计算的金额
     *
     * @param orderType       订单类型, 不同类型价格计算方式不同，可使用枚举值 {@code OrderReqTypeEnum.COMMON_ORDER}
     * @param deliveryType    配送方式, 不同类型价格计算方式不同，可使用枚举值 {@code DeliveryTypeEnum}
     * @param shopId          店铺ID
     * @param productCartList 商品信息字符串，比如："[{\"skuId\":0,\"productId\":81669204}]"
     * @return 总金额
     */
    public static Integer queryOrderPriceFormSettlementPage(Integer orderType, Integer deliveryType,
                                                            Long shopId, List<ProductCart> productCartList) {
        // 计算订单金额，只需要传3个字段， 订单类型、店铺ID、商品信息
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        // 订单类型
        settlementRequestDTO.setOrderType(orderType);
        // 配送方式
        settlementRequestDTO.setDeliveryType(deliveryType);
        // 店铺ID
        settlementRequestDTO.setShopId(shopId);
        // 商品信息
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return settlementProduct(settlementRequestDTO).getResult().getPriceInfo().getTotalAmount();
    }

}
