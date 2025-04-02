package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;

import java.util.Objects;

public class PackingFeeChargeHandler extends AbstractChargeHandler{
    public void getPackingFeeCharge(CalculateOrderBasicData calculateOrderBasicData, OrderCountInfoEx countInfo){
        ShopDTO shop = calculateOrderBasicData.getShop();
        //具体打包费价格，再合并购物车商品信息时，就已经计算完毕了。
        // 打包费
        if (countInfo.getPackingCharges() >= 0) {

            // 打包费=打包费+塑料袋打包费
            int packingCharges = countInfo.getPackingCharges();
            // 此处只统计商品本身的打包费，不计入塑料袋打包费
            Integer plasticAmount = countInfo.getPlasticAmount();
            if (Objects.nonNull(plasticAmount) && packingCharges >= plasticAmount) {
                packingCharges -= plasticAmount;
            }
            if (packingCharges <= 0) {
                //如果打包费不存在，则结束
                return;
            }
//            OrderChargeDetailDTO orderChargeDetail = buildOrderChargeDetailDTO(OrderChargeTypeEnum.PACKING_FEE, packingCharges,shop);

        }

    }
}
