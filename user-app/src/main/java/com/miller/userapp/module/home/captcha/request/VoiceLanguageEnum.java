package com.miller.userapp.module.home.captcha.request;

public enum VoiceLanguageEnum {
    ZH_CN("zh-CN", "中文（普通话）"),
    EN_US("en-US", "英语（美国）");

    private String code;
    private String desc;
    private VoiceLanguageEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
