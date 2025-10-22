package com.miller.service.apifox.enums;

public enum ToolEnum {


    /** 有效 */
    VALID(1),
    /** 无效/下线 */
    INVALID(0);

    private final Integer status;
    private ToolEnum(Integer status) {
        this.status = status;
    }
    public Integer getStatus() {
        return status;
    }

}
