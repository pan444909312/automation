package com.miller.common.util;

/**
 * 统一响应体信息
 *
 * @author Miller Shan
 * @since 2024/7/3 13:57:30
 */
public enum ResponseEnum {

    SUCCESS(0, "success"),

    SUCCESS_200(200, "收到请求"),

    SUCCESS_201(201, "请求处理成功"),

    FAILURE(-1, "failure"),

    REQUEST_ARGS_ERROR(400, "请求参数错误"),

    RESOURCES_NOT_EXIST(404, "请求的资源不存在"),

    REQUEST_METHOD_ERROR(405, "请求方法错误"),

    ACCOUNT_EXPIRE(401, "登录已过期"),

    UNKNOWN_ACCOUNT(401, "未知账号"),

    ACCOUNT_DISABLE(402, "账号已禁用或未登入"),

    ACCOUNT_UNAUTHORIZED(402, "账号无此操作权限"),

    PASSWORD_ERROR(403, "密码错误"),

    FAILURE_SERVICE_ERROR(500, "服务器内部错误"),

    // 较为通用的一些返回值规则
    ID_IS_EMPTY(300, "ID不能为空"),

    RECODE_LESS_THAN_ZERO(301, "影响的记录数小于1，更新或者删除数据失败"),
    ;

    // 系统设置模块 119 00x

    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}