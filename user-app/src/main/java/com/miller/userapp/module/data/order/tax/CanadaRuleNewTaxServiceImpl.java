package com.miller.userapp.module.data.order.tax;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.utils.CollectionUtil;

import com.hungrypanda.app.server.common.utils.DateUtils;
import com.hungrypanda.app.server.dto.order.TaxPriceDTO;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import  com.miller.userapp.module.data.order.tax.config.CaTaxRateConfig;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import com.miller.userapp.module.data.order.tax.config.CaTaxRateEffectiveTimeConfig;
import com.miller.userapp.module.data.order.tax.config.RuleNewTaxConfigEntity;
import com.miller.userapp.module.data.order.tax.config.TaxNumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 加拿大消费税规则
 * 计算规则：（商品售价+商品按比例分摊到的配送费-商品按比例分摊的满减）* 商品消费税
 */

public class CanadaRuleNewTaxServiceImpl implements TaxService {
    /**
     * ca.tax.rate.effective.time.config
     */
    private static final Long[] notFoodShopIdListDef = {123L,124L,919252836L,1734L};
    /**
     * user-app-server.ca.tax.rate.config
     */
    private static final String CaTaxRateConfigDef = """
            {
                 "温哥华,埃德蒙顿": {
                     "orderFeeTaxRate": "5",
                     "productFeeTaxRate": "0",
                     "hpFeeTaxRate": "5"
                 },
                 "多伦多,北多伦多,密西沙加,滑铁卢,伦敦,哈密尔顿": {
                     "orderFeeTaxRate": "13",
                     "productFeeTaxRate": "0",
                     "hpFeeTaxRate": "13"
                 },
                 "温尼伯": {
                     "orderFeeTaxRate": "12",
                     "productFeeTaxRate": "7",
                     "hpFeeTaxRate": "12"
                 },
                 "蒙特利尔,杭州市": {
                     "orderFeeTaxRate": "14.975",
                     "productFeeTaxRate": "9.975",
                     "hpFeeTaxRate": "14.975"
                 }
             }
            """;
    @Override
    public TaxPriceDTO computeTaxPrice(CalculateOrderBasicData ov, OrderCountInfoEx countInfo)  {
        if (countInfo.getTotalPrice() <= 0) {
            return new TaxPriceDTO(0, 0);
        }
        List<ProductCart> productObjectList = ov.getProductCartList();
        //用户端订单消费税
        AtomicInteger taxPrice = new AtomicInteger();
        //商家端订单消费税
        AtomicInteger merTaxPrice = new AtomicInteger();

        ShopDTO shop = ov.getShop();
        String city = shop.getCity();
        Long shopId = shop.getShopId();
        RuleNewTaxConfigEntity ruleNewTaxConfigEntity = ov.getRuleNewTaxConfigEntity();
        List<Long> notFoodShopIdList = ruleNewTaxConfigEntity.getCaTaxNotFoodShopIdList();
        if(Objects.isNull(notFoodShopIdList)){
            notFoodShopIdList = Arrays.asList(notFoodShopIdListDef);
        }
        Map<String, CaTaxRateConfig> caTaxRateMap = ruleNewTaxConfigEntity.getCaTaxRateMap();
        //是否为餐饮店铺
        boolean isFoodShop = CollectionUtil.isEmpty(notFoodShopIdList) || !notFoodShopIdList.contains(shopId);
        /*
         * 餐饮：(平台打包费+平台服务费-会员平台服务费减免+配送费)*平台费用税率%+(商品售价-商家承担满减)*商品税率%
         * 非餐饮：(商品售价+((配送费-商家承担满减+平台打包费+平台服务费-会员平台服务费减免)* 商品售价占比))*订单税率%
         * 未启用：(商品售价+((配送费-商家承担满减+平台打包费+平台服务费-会员平台服务费减免)* 商品售价占比))*商品消费税率%
         */
        try {
            CaTaxRateConfig caTaxRateConfig = getCaTaxRateConfig(city,caTaxRateMap);
            if (caTaxRateConfig == null || !isConfigEffective(ov.getTimeZone(),ruleNewTaxConfigEntity.getCaTaxRateEffectiveTimeConfig())) {
                calDefaultTax(productObjectList, ov, countInfo, taxPrice, merTaxPrice);
            } else {
                if(isFoodShop) {//餐饮：(平台打包费+平台服务费-会员平台服务费减免+配送费)*平台费用税率%+(商品售价-商家承担满减)*商品税率%
                    calFoodShopTax(caTaxRateConfig, productObjectList, ov, countInfo, taxPrice, merTaxPrice);
                } else {//非餐饮：(商品售价+((平台打包费+平台服务费-会员平台服务费减免+配送费-商家承担满减)* 商品售价占比))*订单税率%
                    calNotFoodShopTax(caTaxRateConfig, productObjectList, ov, countInfo, taxPrice, merTaxPrice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //兜底走默认消费税
            calDefaultTax(productObjectList, ov, countInfo, taxPrice, merTaxPrice);
        }
        return new TaxPriceDTO(taxPrice.intValue(), merTaxPrice.intValue());

    }
    /**
     * 不符合生效区间-使用默认：(商品售价+((配送费-商家承担满减+平台打包费+平台服务费-会员平台服务费减免)* 商品售价占比))*商品消费税率%
     */
    private void calDefaultTax(List<ProductCart> productObjectList, CalculateOrderBasicData ov, OrderCountInfoEx countInfo, AtomicInteger taxPrice, AtomicInteger merTaxPrice) {
        //订单消费税率
        //平台打包费+平台服务费-会员平台服务费减免+配送费-商家承担满减
        int hpFee = countInfo.getPlatformPacking()+countInfo.getPlatformFee()-countInfo.getMemberPlatformFeeDiscount()+countInfo.getDeliveryPriceAddDeliveryChargeAmount()-ov.getFullSubMerchant();
        for (ProductCart p : productObjectList) {
            BigDecimal productFeeTaxRate = TaxNumberUtils.divideBd(6, p.getTaxRate(), 100);
            BigDecimal percent = TaxNumberUtils.divideBd(6, p.getPrice(), countInfo.getTotalPrice());
            BigDecimal subHpFee = TaxNumberUtils.multiply(0, hpFee, percent);//单位，分
            int subTax = TaxNumberUtils.multiply(0,TaxNumberUtils.addObj(subHpFee, p.getPrice()), productFeeTaxRate).intValue();
            taxPrice.addAndGet(subTax);

            BigDecimal merPercent = TaxNumberUtils.divideBd(6, p.getMerPrice(), countInfo.getMerTotalPrice());
            BigDecimal subMerHpFee = TaxNumberUtils.multiply(0, hpFee, merPercent);//单位，分
            int subMerTax = TaxNumberUtils.multiply(0,TaxNumberUtils.addObj(subMerHpFee, p.getMerPrice()), productFeeTaxRate).intValue();
            merTaxPrice.addAndGet(subMerTax);
            p.setTaxPrice(subMerTax);
        }
    }

    //(平台打包费+平台服务费-会员平台服务费减免+配送费)*平台费用税率%+(商品售价-商家承担满减)*商品税率%
    private void calFoodShopTax(CaTaxRateConfig caTaxRateConfig, List<ProductCart> productObjectList, CalculateOrderBasicData ov, OrderCountInfoEx countInfo, AtomicInteger taxPrice, AtomicInteger merTaxPrice) {
        //平台费用消费税率
        BigDecimal hpFeeTaxRate = TaxNumberUtils.divideBd(6, caTaxRateConfig.getHpFeeTaxRate(), 100);
        //商品消费税率
        BigDecimal productFeeTaxRate = TaxNumberUtils.divideBd(6, caTaxRateConfig.getProductFeeTaxRate(), 100);

        int foodShopHpFee = countInfo.getPlatformPacking()+countInfo.getPlatformFee()-countInfo.getMemberPlatformFeeDiscount()+countInfo.getDeliveryPriceAddDeliveryChargeAmount();
        //平台费用 = 平台打包费 + 平台服务费新增收费项 + 平台服务费 - 会员平台服务费减免 + 配送费
        //平台费用消费税(单位，分)
        BigDecimal hpFeeTax = TaxNumberUtils.multiply(0, foodShopHpFee, hpFeeTaxRate);

        //用户端商品费用
        int productFee = countInfo.getTotalPrice() - ov.getFullSubMerchant();
        //用户端商品费用消费税(单位，分)
        BigDecimal productFeeTax = TaxNumberUtils.multiply(0, productFee, productFeeTaxRate);

        //商家端商品费用
        int merProductFee = countInfo.getMerTotalPrice() - ov.getFullSubMerchant();
        //商家端商品费用消费税(单位，分)
        BigDecimal merProductFeeTax = TaxNumberUtils.multiply(0, merProductFee, productFeeTaxRate);

        BigDecimal taxTotal = hpFeeTax.add(productFeeTax);
        BigDecimal merTaxTotal = hpFeeTax.add(merProductFeeTax);

        for (ProductCart p : productObjectList) {
            BigDecimal percent = TaxNumberUtils.divideBd(6, p.getPrice(), countInfo.getTotalPrice());
            taxPrice.addAndGet(TaxNumberUtils.multiply(0,taxTotal,percent).intValue());

            BigDecimal merPercent = TaxNumberUtils.divideBd(6, p.getMerPrice(), countInfo.getMerTotalPrice());
            int merTax = TaxNumberUtils.multiply(0,merTaxTotal,merPercent).intValue();
            merTaxPrice.addAndGet(merTax);
            p.setTaxPrice(merTax);
        }
    }

    //(商品售价+((配送费-商家承担满减+平台打包费+平台服务费-会员平台服务费减免)* 商品售价占比))*订单税率%
    private void calNotFoodShopTax(CaTaxRateConfig caTaxRateConfig, List<ProductCart> productObjectList, CalculateOrderBasicData ov, OrderCountInfoEx countInfo, AtomicInteger taxPrice, AtomicInteger merTaxPrice) {
        //订单消费税率
        BigDecimal orderFeeTaxRate = TaxNumberUtils.divideBd(6, caTaxRateConfig.getOrderFeeTaxRate(), 100);
        //平台打包费+平台服务费-会员平台服务费减免+配送费-商家承担满减
        int unFoodShopHpFee = countInfo.getPlatformPacking()+countInfo.getPlatformFee()-countInfo.getMemberPlatformFeeDiscount()+countInfo.getDeliveryPriceAddDeliveryChargeAmount()-ov.getFullSubMerchant();
        for (ProductCart p : productObjectList) {
            BigDecimal percent = TaxNumberUtils.divideBd(6, p.getPrice(), countInfo.getTotalPrice());
            BigDecimal hpFee = TaxNumberUtils.multiply(0, unFoodShopHpFee, percent);//单位，分
            int tax = TaxNumberUtils.multiply(0,TaxNumberUtils.addObj(hpFee, p.getPrice()), orderFeeTaxRate).intValue();
            taxPrice.addAndGet(tax);

            BigDecimal merPercent = TaxNumberUtils.divideBd(6, p.getMerPrice(), countInfo.getMerTotalPrice());
            BigDecimal merHpFee = TaxNumberUtils.multiply(0, unFoodShopHpFee, merPercent);//单位，分
            int merTax = TaxNumberUtils.multiply(0,TaxNumberUtils.addObj(merHpFee, p.getMerPrice()), orderFeeTaxRate).intValue();
            merTaxPrice.addAndGet(merTax);
            p.setTaxPrice(merTax);
        }
    }

    /**
     * 根据city获取税率配置
     */
    private CaTaxRateConfig getCaTaxRateConfig(String city, Map<String, CaTaxRateConfig> caTaxRateMap) {
        if(CollectionUtil.isEmpty(caTaxRateMap)) {
            caTaxRateMap = JSON.parseObject(CaTaxRateConfigDef, new TypeReference<Map<String, CaTaxRateConfig>>() {});
        }
        for (String key : caTaxRateMap.keySet()) {
            List<String> cityKey = Arrays.asList(key.split(","));
            if(cityKey.contains(city)) {
                return caTaxRateMap.get(key);
            }
        }
        return null;
    }
    /**
     * 获取ca税率生效区间配置，返回当前是否生效
     */
    private boolean isConfigEffective(String timezone, CaTaxRateEffectiveTimeConfig caTaxRateEffectiveTimeConfig) {
        if(Objects.isNull(caTaxRateEffectiveTimeConfig)) {
            return false;
        }
        if(StringUtils.isBlank(caTaxRateEffectiveTimeConfig.getStartDate())
                || StringUtils.isBlank(caTaxRateEffectiveTimeConfig.getEndDate())
                || StringUtils.isBlank(timezone)) {
            return false;
        }

        long now = System.currentTimeMillis();
        long start = DateUtils.getTimestampByTimeZone(caTaxRateEffectiveTimeConfig.getStartDate(), timezone, "yyyy-MM-dd");
        long end = DateUtils.getTimestampByTimeZone(caTaxRateEffectiveTimeConfig.getEndDate(), timezone, "yyyy-MM-dd");
        end = end + 24 * 60 * 60 * 1000;//+1 day
        return now >= start && now < end;
    }

}
