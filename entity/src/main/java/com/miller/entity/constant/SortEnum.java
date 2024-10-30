package com.miller.entity.constant;

/**
 * @author panjuxiang
 * @since 2024/10/10 20:36
 */
public enum SortEnum {
    SORT_SCENARIO_ID("1", "scenario_id"),
    SORT_SCENARIO_NAME("2", "scenario_name"),
    SORT_DEVELOPMENT_TIME("3", "development_time"),
    SORT_MAINTENANCE_TIME("4", "maintenance_time"),
    SORT_MANUAL_TEST_TIME("5", "manual_test_time"),
    SORT_ROI("6", "roi"),
    SORT_CREATE_TIME("7", "create_time");

    // 私有字段，存储数据库中的值
    private final String key;

    // 私有字段，存储描述信息
    private final String value;

    // 私有构造函数，用于初始化枚举常量
    private SortEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (SortEnum sortEnum : values()) {
            if (sortEnum.key.equals(key)) {
                return sortEnum.value;
            }
        }
        //默认返回根据create_time排序
        return SORT_CREATE_TIME.value;
    }

    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }
}
