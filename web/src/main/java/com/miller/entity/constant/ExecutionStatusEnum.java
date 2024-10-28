package com.miller.entity.constant;

/**
 * @author panjuxiang
 * @since 2024/10/22 10:10
 */
public enum ExecutionStatusEnum {



    ERROR(-1, "异常"),

    SUCCESS(1, "成功"),

    FAIL(2, "失败"),
    PASS(3, "跳过"),
    DISABLE(4, "禁用"),
    ;


    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    ExecutionStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getValueByKey(int key) {
        for (ExecutionStatusEnum executionTypeEnum : values()) {
            if (executionTypeEnum.code == key) {
                return executionTypeEnum.message;
            }
        }
        //默认返回根据create_time排序
        throw new IllegalArgumentException("没有找到对应的状态");
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
