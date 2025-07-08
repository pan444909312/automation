package com.miller.deliveryapp.constants;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: panjuxiang
 * @Since: 2025/7/8
 */
@Getter
public enum OrderDeliveryOptTypeEnum {
    ON_SHOP((byte) 1, "到店"),
    NON_OUT_MEAL((byte) 2, "未出餐"),
    TAKE_MEAL((byte) 3, "取餐"),
    DELIVERY_CONSUMER((byte) 6, "送达"),
    WAIT_MEAL((byte) 7, "等餐"),

    ;


    public static List<OrderDeliveryOptTypeEnum> list() {
        List<OrderDeliveryOptTypeEnum> list = new ArrayList<>();
        list.add(ON_SHOP);
        list.add(TAKE_MEAL);
        list.add(WAIT_MEAL);
        list.add(NON_OUT_MEAL);
        list.add(DELIVERY_CONSUMER);
        return list;
    }

    private byte value;
    private String desc;

    private OrderDeliveryOptTypeEnum(byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public static OrderDeliveryOptTypeEnum getTypeOf(Byte value) {
        if (null == value) {
            return null;
        }
        for (OrderDeliveryOptTypeEnum ifte : OrderDeliveryOptTypeEnum.values()) {
            if (ifte.value == value.byteValue()) {
                return ifte;
            }
        }
        return null;
    }

    public static String valuesOf(Byte value) {
        if (null == value) {
            return "";
        }
        for (OrderDeliveryOptTypeEnum ifte : OrderDeliveryOptTypeEnum.values()) {
            if (ifte.value == value.byteValue()) {
                return ifte.getDesc();
            }
        }
        return "";
    }

}
