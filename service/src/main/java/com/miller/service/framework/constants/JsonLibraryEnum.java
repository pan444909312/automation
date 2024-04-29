package com.miller.service.framework.constants;

/**
 * JSON 序列化库
 *
 * <p>
 *     支持 FASTJSON，JACKSON
 * </p>
 * @author Miller Shan
 * @version 1.0
 * @since 2024/4/29 17:27:08
 */
public enum JsonLibraryEnum {
    JACKSON("jackson"),
    FASTJSON("fastjson"),
    GSON("gson");
    ;

    private String value;

    JsonLibraryEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
