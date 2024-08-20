package com.miller.merchant.admin.product.list.request;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * platform 服务接口，没有对应的DTO ，只能自己定义
 */
@Data
public class ProductListDTO {

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     *
     */
    private int type = 0;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品编码
     */
    private String proNum;

    /**
     * 菜单ID
     */
    private String menuId;

    private String select;

    private String proNumber;

    private String productStatus;


    private String isSpecial;

    private String productIsLimit;

    private String productPriceMin;
    private String productPriceMax;
    private String isPersonalRecommend;
    private String isCombine;

// TODO 对象转Map ，未完成
//    public Map<String,Object> toMap(){
//        Map<String,Object> map = new HashMap<>();
//        Field[] fields = this.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            map.put(field.getName(),field.get())
//        }
//    }
}
