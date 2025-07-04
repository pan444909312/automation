package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.common.enums.EntryInfoEnum;
import com.hungrypanda.app.server.common.enums.order.UsePlasticEnum;
import com.hungrypanda.app.server.common.utils.PriceUtil;
import com.hungrypanda.app.server.common.utils.TranslateUtils;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

public class PlasticPackingFeeChargeHandler extends AbstractChargeHandler{
    public void getPlasticPackingFeeChargeWithPlasticMessage(CalculateOrderBasicData calculateOrderBasicData, OrderCountInfoEx countInfo){
        ShopDTO shop = calculateOrderBasicData.getShop();
        String plasticMessage = null;
        //如果开启了塑料袋打包费，且塑料袋打包费不为0
        if (shop.getUsePlastic().equals(UsePlasticEnum.USE_PLASTIC.getCode()) && !NumberUtils.INTEGER_ZERO.equals(shop.getPlasticAmount())) {
            String plasticAmountStr = PriceUtil.getFixedPriceStr(shop.getPlasticAmount(), shop.getCountry());
            if (countInfo.getPackingCharges() == 0) {
                calculateOrderBasicData.setUsePlasticMessage(String.format(TranslateUtils.trans(EntryInfoEnum.USE_PLASTIC_MESSAGE), plasticAmountStr));
            } else {
                String packingStr = PriceUtil.getFixedPriceStr(countInfo.getPackingCharges(), shop.getCountry());
                calculateOrderBasicData.setUsePlasticMessage(String.format(TranslateUtils.trans(EntryInfoEnum.USE_PLASTIC_PACKING_MESSAGE), packingStr, plasticAmountStr));
            }
            countInfo.addPackingCharges(shop.getPlasticAmount());
            countInfo.setPlasticAmount(shop.getPlasticAmount());
            if (Objects.isNull(shop.getPlasticAmount()) || shop.getPlasticAmount() <= 0) {
                //不存在塑料袋打包费时，直接结束
                return ;
            }
            calculateOrderBasicData.setPackingCharges(countInfo.getPackingCharges());
//            OrderChargeDetailDTO orderChargeDetail = buildOrderChargeDetailDTO(OrderChargeTypeEnum.PLASTIC_PACKING_FEE, shop.getPlasticAmount(), shop);
        }

    }

}
