package com.miller.entity.report.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApifoxAutoCaseRoiDto {


    @Schema(description = "场景id")
    private String scenarioId;

    @Schema(description = "场景名称")
    private String scenarioName;

    @Schema(description = "开发成本")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    private Integer manualTestTime;


    @Schema(description = "执行人员名称")
    private String executionUser;

}
