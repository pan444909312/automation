package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 自动化用例执行记录表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-10
 */
@Data
@TableName("auto_execution_record")
@Schema(name = "AutoExecutionRecord", description = "自动化用例执行记录表")
public class AutoExecutionRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "执行记录ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "场景id")
    @TableField("scenario_id")
    private String scenarioId;

    @Schema(description = "执行策略 0:未知策略1:日常巡检;2:质量保证;3:效率提升")
    @TableField("execution_type")
    private Integer executionType;

    @Schema(description = "执行时间")
    @TableField("execution_time")
    private Long executionTime;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    @TableField("execution_status")
    private Integer executionStatus;

    @Schema(description = "执行人员名称")
    @TableField("execution_user")
    private String executionUser;

    @Schema(description = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Long createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @Schema(description = "删除标记（0:可用 1:不可用）")
    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;

    @Schema(description = "开发成本")
    @TableField("development_time")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    @TableField("maintenance_time")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    @TableField("manual_test_time")
    private Integer manualTestTime;
}
