package com.miller.entity.report.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: panjuxiang
 * @Since: 2025/8/19
 */
@Data
public class AutoCaseExecutionDailyDTO {

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

    @Schema(description = "用例负责人，邮箱地址")
    @TableField("author")
    private String author;

    @Schema(description = "场景名称")
    private String scenarioName;

    @Schema(description = "关联项目id")
    private Integer projectId;

}
