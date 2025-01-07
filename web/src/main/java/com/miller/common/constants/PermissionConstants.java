package com.miller.common.constants;

/**
 * 权限常量类。<b>注意:需要与数据库表 permission 中的 permission_code 字段对应</b>
 *
 * @author Miller Shan
 * @since 2024-12-15 12:22:13
 */
public class PermissionConstants {
    // 缺陷管理
    public static final String ISSUES = "issues";
    public static final String ISSUES_LIST = "issues:list";

    // 系统管理-用户管理
    public static final String MANAGER = "manage";
    public static final String MANAGER_USER = "manage:user";
}
