package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 自动化用例执行趋势表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-11-05
 */
@Getter
@Setter
@TableName("auto_case_chart_future_data")
@Schema(name = "AutoCaseChartFutureData", description = "自动化用例执行趋势表")
public class AutoCaseChartFutureDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "未来日期")
    @TableField("future_time")
    private Long futureTime;

    @Schema(description = "报表类型 1：场景总ROI表 2：用例增长趋势表 3：用例执行趋势表")
    @TableField("chart_type")
    private Integer chartType;

    @Schema(description = "执行策略 0:未知策略 1:日常巡检;2:质量保证;3:效率提升（仅chart_type=1的表该字段有用）")
    private Integer executionType;

    @Schema(description = "预计累计收益")
    @TableField("expected_save_time")
    private Long expectedSaveTime;

    @Schema(description = "预计新增用例数")
    @TableField("expected_increase_case")
    private Integer expectedIncreaseCase;

    @Schema(description = "预计执行用例数")
    @TableField("expected_execution_case")
    private Integer expectedExecutionCase;

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
}
