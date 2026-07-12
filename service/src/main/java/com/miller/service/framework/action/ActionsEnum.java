package com.miller.service.framework.action;

/**
 * 框架支持的关键字
 *
 * @author Miller Shan
 * @version 1.0.0
 */
public enum ActionsEnum {
    /**
     * 系统内置支持的枚举
     */
    none,                           // 无
    invariantValue,                 // 固定值
    equals,                         // 相等判断
    ;

    /**
     * 判断一个字符串是否是 一个枚举类型
     */
    public static boolean isEnum(String string) {
        // string = string.toUpperCase();
        for (ActionsEnum enumActions : ActionsEnum.values()) {
            // System.out.println("遍历枚举值:" + enumActions.name());
            if (enumActions.name().equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }
}
