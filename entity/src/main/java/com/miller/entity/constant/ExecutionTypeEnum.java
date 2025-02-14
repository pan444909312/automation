package com.miller.entity.constant;

/**
 * @author panjuxiang
 * @since 2024/10/22 10:10
 */
public enum ExecutionTypeEnum {


    UNKNOWN_STRATEGY(-1, "异常策略"),
    DEFAULT_STRATEGY(0, "默认策略"),
    DAILY_CHECK(1, "日常巡检"),

    QUALITY_ASSURANCE(2, "质量保证"),

    EFFICIENCY_IMPROVEMENT(3, "效率提升"),

    ;


    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    ExecutionTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getValueByKey(int key) {
        for (ExecutionTypeEnum executionTypeEnum : values()) {
            if (executionTypeEnum.code == key) {
                return executionTypeEnum.message;
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
