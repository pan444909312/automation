package com.miller.entity.constant;

import javax.crypto.interfaces.PBEKey;

/**
 * @author panjuxiang
 * @since 2025/3/7 10:28
 */
public enum ProjectTypeEnum {


    PROJECT_B(1, "B端-商家组", "1"),

    PROJECT_C(2, "C端-导购组", "2"),

    PROJECT_D(3, "D端-骑手组", "3"),

    PROJECT_P(4, "P端-平台组", "4"),

    ;


    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    private final String projectId;

    ProjectTypeEnum(int code, String message, String projectId) {
        this.code = code;
        this.message = message;
        this.projectId = projectId;
    }

    public static String getValueByKey(int key) {
        for (ProjectTypeEnum projectTypeEnum : values()) {
            if (projectTypeEnum.code == key) {
                return projectTypeEnum.projectId;
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

    public String getProjectId() {
        return projectId;
    }
}
