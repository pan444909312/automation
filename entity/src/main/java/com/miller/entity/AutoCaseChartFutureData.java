package com.miller.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class AutoCaseChartFutureData implements Serializable {

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
    @TableField("create_time")
    private Long createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private Long updateTime;

    @Schema(description = "删除标记（0:可用 1:不可用）")
    @TableField("is_deleted")
    private Byte isDeleted;
}
