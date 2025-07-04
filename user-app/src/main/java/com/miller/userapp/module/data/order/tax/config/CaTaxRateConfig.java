package com.miller.userapp.module.data.order.tax.config;

import lombok.Data;

@Data
public class CaTaxRateConfig {

    /**
     * 平台费用消费税率
     */
    private String hpFeeTaxRate;

    /**
     * 商品费用消费税率
     */
    private String productFeeTaxRate;

    /**
     * 订单费用消费税率
     */
    private String orderFeeTaxRate;


}
