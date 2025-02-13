package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 自动化用例执行趋势表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@Data
@TableName("auto_case_execution_chart")
@Schema(name = "AutoCaseExecutionChart", description = "自动化用例执行趋势表")
public class AutoCaseExecutionChartEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "日期")
    @TableField("chart_date")
    private String chartDate;

    @Schema(description = "执行用例数")
    @TableField("execution_case")
    private Integer executionCase;

    @Schema(description = "执行成功数量")
    @TableField("execution_success_time")
    private Integer executionSuccessTime;

    @Schema(description = "执行失败数量")
    @TableField("execution_fail_time")
    private Integer executionFailTime;

    @Schema(description = "备注")
    @TableField("remarks")
    private String remarks;

    @Schema(description = "执行策略 0:未知策略 1:日常巡检;2:质量保证;3:效率提升")
    @TableField("execution_type")
    private Integer executionType;

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

    @Schema(description = "关联项目id")
    @TableField("project_id")
    private String projectId;

}
