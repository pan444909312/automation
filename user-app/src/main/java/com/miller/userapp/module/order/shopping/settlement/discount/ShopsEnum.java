package com.miller.userapp.module.order.shopping.settlement.discount;

public enum ShopsEnum {
    SKUDiscount(615477825L,"skuDiscount",1),
    SHOPDiscount(931800485L,"shopDiscount",2);
    private Long shopId;
    private String desc;
    private Integer type;
    private ShopsEnum(Long shopId,String desc,Integer type){
        this.shopId = shopId;
        this.desc = desc;
        this.type = type;
    }

    public Long getShopId() {
        return shopId;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getType() {
        return type;
    }
}
