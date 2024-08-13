package com.miller.service.dto;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/13 10:38:19
 */
public enum XXLConfigEnvEnum {
    TEST("test", "测试环境"),
    DEV("dev", "开发环境");

    private String env;
    private String desc;

    XXLConfigEnvEnum(String env, String desc) {
        this.env = env;
        this.desc = desc;
    }

    public String getEnv() {
        return env;
    }

    public String getDesc() {
        return desc;
    }
}
