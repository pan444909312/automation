package com.miller.userapp.module.data.order.handler;


import com.hungrypanda.app.server.dto.shop.ShopDTO;

import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

public class TablewareFeeChargeHandler extends AbstractChargeHandler {
    public void getTablewareFeeCharge(CalculateOrderBasicData calculateOrderBasicData, OrderCountInfoEx countInfo){
        Integer tablewareCount = calculateOrderBasicData.getTablewareCount();
        ShopDTO shop = calculateOrderBasicData.getShop();
        // 餐具费
        int tablewarePrice = shop.getTablewarePrice();
        // 默认餐具数量
        Integer defaultTablewareQuantity = shop.getDefaultTablewareQuantity();

        //获取餐具数量
        if (Objects.isNull(tablewareCount)) {
            if (Objects.nonNull(defaultTablewareQuantity)) {
                //如果存在默认餐具数量，则使用默认餐具数量
                tablewareCount = defaultTablewareQuantity;
            } else {
                //兜底，餐具数量为1
                tablewareCount = NumberUtils.INTEGER_ONE;
            }
        }

        //计算餐具费
        tablewarePrice = tablewareCount * tablewarePrice;

        // 设置餐具费相关信息
        if (tablewarePrice > 0) {
            countInfo.setTablewarePrice(tablewarePrice);
            calculateOrderBasicData.setTablewarePrice(tablewarePrice);
//            OrderChargeDetailDTO orderChargeDetail = buildOrderChargeDetailDTO(OrderChargeTypeEnum.TABLEWARE_FEE, tablewarePrice, shop);
        }
        calculateOrderBasicData.setTablewareCount(tablewareCount);

    }
}
