package com.miller.userapp.order.shopping.settlement.provider;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.userapp.order.shopping.settlement.request.SettlementRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 数据提供者_结算页(由于历史原因，也叫创建虚单)
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 14:54:33
 */
@SuppressWarnings("unused")
public class SettlementDataProvider {

    static Stream<Arguments> settlementProduct() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(1);
        settlementRequestDTO.setPayType(0);
        settlementRequestDTO.setAutoUseRedPacketStatus(1);
        settlementRequestDTO.setOrderReqType(0);
        settlementRequestDTO.setDeliveryType(0);
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);
        settlementRequestDTO.setAddressId(0L);
        // settlementRequestDTO.setVerify(0);

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        //createOrderByMyselfDelivery.setProductCartList("[{\"productId\":81669204,\"skuId\":0,\"tagId\":[]}]");
        List<ProductCart> productCartList = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
}
