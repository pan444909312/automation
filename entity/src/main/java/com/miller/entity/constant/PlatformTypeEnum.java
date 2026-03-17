package com.miller.entity.constant;

/**
 * @author panjuxiang
 * @since 2024/10/22 10:10
 */
public enum PlatformTypeEnum {

    OTHER(0, "OTHER"),
    JAVA(1, "JAVA"),

    APIFOX(2, "APIFOX"),

    JMETER(3, "JMETER"),

    UI_IOS(4, "UI自动化-IOS"),
    UI_ANDROID(5, "UI自动化-ANDROID"),
    UI_WEB(6, "UI自动化-WEB"),

    ;


    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    PlatformTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getValueByKey(int key) {
        for (PlatformTypeEnum executionTypeEnum : values()) {
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
