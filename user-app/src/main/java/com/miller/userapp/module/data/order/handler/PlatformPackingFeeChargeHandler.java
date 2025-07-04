package com.miller.userapp.module.data.order.handler;


import com.hungrypanda.app.server.common.utils.StringUtils;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.miller.userapp.module.data.basic.service.SysAppConfigService;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Arrays;

public class PlatformPackingFeeChargeHandler extends AbstractChargeHandler{
    private SysAppConfigService sysAppConfigService;
    public PlatformPackingFeeChargeHandler(SqlSession sqlSession){
        sysAppConfigService = new SysAppConfigService(sqlSession);
    }
    public void getPlatformPackingFeeCharge(CalculateOrderBasicData calculateOrderBasicData, OrderCountInfoEx countInfo){
        try {
            ShopDTO shop = calculateOrderBasicData.getShop();
            //没有打包费,就根据配置收取平台打包费
            if (countInfo.getPackingCharges() == 0) {

                String shopWhiteList = sysAppConfigService.getSysAppConfigByKey("PLATFORM_PACKING_FEE_SHOP_WHITE_LIST");
                if(StringUtils.isNotBlank(shopWhiteList)
                        && Arrays.asList(shopWhiteList.split(",")).contains(shop.getShopId()+"")) {
                    //白名单不为空,且当前商家id在白名单内，不收取平台打包费
                    return;
                }
                String sysAppConfigByKey = sysAppConfigService.getSysAppConfigByKey("PLATFORM_PACKING_FEE_CONFIG");
                if (StringUtils.isBlank(sysAppConfigByKey) || !NumberUtils.isNumber(sysAppConfigByKey)) {
                    return;
                }
                //按每单收取
                int platformPacking = Integer.parseInt(sysAppConfigByKey);
                if (platformPacking <= 0) {
                    return;
                }
                calculateOrderBasicData.setPlatformPackaging(platformPacking);
//                OrderChargeDetailDTO orderChargeDetail = buildOrderChargeDetailDTO(OrderChargeTypeEnum.PLATFORM_PACKING_FEE, platformPacking, shop);
                countInfo.setPlatformPacking(platformPacking);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }

    }
}
