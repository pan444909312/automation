package com.miller.userapp.module.data.order.entity;


import com.hungrypanda.app.server.dto.order.OrderVirtual;
import com.hungrypanda.app.server.dto.redpacket.CdKeyModel;
import com.miller.userapp.module.data.order.tax.config.RuleNewTaxConfigEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CalculateOrderBasicData extends OrderVirtual {
    /**
     * 商品售价汇总(包含加料费用)
     */
//    private Integer totalPrice;
    /**
     * 商品是否使用单品折扣优惠
     */
//    private boolean hasDiscountPromote;
    /**
     * 商品是否使用门店新人优惠
     */
//    private boolean hasNewDiscountPromote;
//    private ShopDTO shopDTO;
    /**
     * "userId":249296,驼峰
     */
    private Map<String,Object> header;
    /**
     * 活动类型，0常规下单,1裂变活动
     */
    private Integer activityCode;
    /**
     * 配送类型
     */
    private String deliveryType;
    /**
     * 新消费税规则配置
     */
    private RuleNewTaxConfigEntity ruleNewTaxConfigEntity;
    /**
     * 餐具数目
     */
//    private Integer tablewareCount;
    /**
     * 选中的红包
     */
    private CdKeyModel cdKeyModel;
    /**
     * 购物车商品信息
     */
//    private List<ProductCart> productCartList;
}
