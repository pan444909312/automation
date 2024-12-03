package com.miller.entity.report.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApifoxAutoCaseRoiDto {


    @Schema(description = "场景id")
    private String scenarioId;

    @Schema(description = "场景名称")
    private String scenarioName;

    @Schema(description = "执行策略 1:日常巡检;2:质量保证;3:效率提升")
    private Integer executionType;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    private Integer executionStatus;

    @Schema(description = "开发成本")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    private Integer manualTestTime;

    @Schema(description = "执行人员名称")
    private String executionUser;

}
