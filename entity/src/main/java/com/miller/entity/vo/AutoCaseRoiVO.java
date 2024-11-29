package com.miller.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/9/29 11:23
 */
@Data
public class AutoCaseRoiVO {
    @Schema(description = "ID 自增")
    private Long id;

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

    @Schema(description = "执行次数")
    private Integer times;

    @Schema(description = "总节省成本")
    private long saveTime;

    @Schema(description = "场景ROI")
    private String roi;

    @Schema(description = "执行人员名称")
    private String executionUser;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

}
