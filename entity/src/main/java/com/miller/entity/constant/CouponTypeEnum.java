package com.miller.entity.constant;

/**
 * @author panjuxiang
 * @since 2025/3/7 10:28
 */
public enum CouponTypeEnum {


    FULL_SUB(1, "满减券"),

    DISCOUNT(2, "折扣券"),

    DELIVERY_FEE(3, "运费券"),

    ;


    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;


    CouponTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getValueByKey(int key) {
        for (CouponTypeEnum projectTypeEnum : values()) {
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
