package com.miller.entity.constant;

/**
 * @author panjuxiang
 * @since 2025/3/7 10:28
 */
public enum RunTeatCaseTypeEnum {


    TASK(1, "定时任务执行"),

    DEBUG(2, "调试执行"),

    PLATFORM(3, "平台执行"),

    ;


    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;


    RunTeatCaseTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getValueByKey(int key) {
        for (RunTeatCaseTypeEnum projectTypeEnum : values()) {
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
