package com.miller.userapp.module.data.order.entity;

import lombok.Data;

@Data
public class SuperValueExchangeEntity {
    /**
     * 是否有超值换购商品
     */
    private boolean hasSuperValueExchange;
    /**
     * 超值换购商品总价
     */
    private Integer superValueExchangePrice;
}
