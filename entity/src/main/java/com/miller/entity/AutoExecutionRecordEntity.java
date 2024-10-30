package com.miller.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/10/23 17:52
 */
@Data
public class AutoExecutionRecordEntity {
    @Schema(description = "执行记录ID 自增")
    private Long id;

    @Schema(description = "场景id")
    private String scenarioId;

    @Schema(description = "执行策略 1:日常巡检;2:质量保证;3:效率提升")
    private String executionType;

    @Schema(description = "执行时间")
    private Long executionTime;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    private String executionStatus;

    @Schema(description = "执行人员名称")
    private String executionUser;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "更新时间")
    private Long updateTime;

    @Schema(description = "删除标记（0:可用 1:不可用）")
    private Byte isDeleted;

    @Schema(description = "场景名称")
    private String scenarioName;

    @Schema(description = "开发成本")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    private Integer manualTestTime;
}
