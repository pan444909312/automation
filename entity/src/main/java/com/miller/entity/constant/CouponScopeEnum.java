package com.miller.entity.constant;

/**
 * @author panjuxiang
 * @since 2025/3/7 10:28
 */
public enum CouponScopeEnum {


    SHOP_COUPON(1, "商家券"),

    PRODUCT_COUPON(2, "商品券"),

    PLATFORM_COUPON(3, "平台券"),
    SUPER_COUPON(4, "神券"),

    ;


    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;


    CouponScopeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getValueByKey(int key) {
        for (CouponScopeEnum projectTypeEnum : values()) {
            if (projectTypeEnum.code == key) {
                return projectTypeEnum.message;
            }
        }
        //默认返回根据create_time排序
        throw new IllegalArgumentException("没有找到对应的类型");
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
