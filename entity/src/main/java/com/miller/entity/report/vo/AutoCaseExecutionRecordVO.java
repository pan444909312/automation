package com.miller.entity.report.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/10/22 10:16
 */
@Data
public class AutoCaseExecutionRecordVO {
    @Schema(description = "执行记录ID 自增")
    private Long id;

    @Schema(description = "场景id")
    @TableField("scenario_id")
    private String scenarioId;

    @Schema(description = "执行策略 1:日常巡检;2:质量保证;3:效率提升")
    @TableField("execution_type")
    private Integer executionType;

    @Schema(description = "执行策略 1:日常巡检;2:质量保证;3:效率提升")
    private String executionTypeDesc;

    @Schema(description = "执行时间")
    private String executionTime;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    @TableField("execution_status")
    private Integer executionStatus;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    private String executionStatusDesc;

    @Schema(description = "执行人员名称")
    @TableField("execution_user")
    private String executionUser;

    @Schema(description = "场景名称")
    private String scenarioName;

    @Schema(description = "开发成本")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    private Integer manualTestTime;


}
