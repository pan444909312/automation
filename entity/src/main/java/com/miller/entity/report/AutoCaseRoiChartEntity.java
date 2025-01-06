package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 测试场景总ROI表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@Data
@NoArgsConstructor
@TableName("auto_case_roi_chart")
@Schema(name = "AutoCaseRoiChartEntity", description = "测试场景总ROI表")
public class AutoCaseRoiChartEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "日期")
    @TableField("chart_date")
    private String chartDate;

    @Schema(description = "累计维护成本")
    @TableField("total_maintenance_time")
    private Long totalMaintenanceTime;

    @Schema(description = "累计开发成本")
    @TableField("total_development_time")
    private Long totalDevelopmentTime;

    @Schema(description = "累计执行次数")
    @TableField("times")
    private Integer times;

    @Schema(description = "累计收益")
    @TableField("save_time")
    private Long saveTime;

    @Schema(description = "场景ROI")
    @TableField("roi")
    private String roi;

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

    public AutoCaseRoiChartEntity(Long totalMaintenanceTime, Long totalDevelopmentTime, Integer times, Long saveTime, Double roi, Integer executionType,String timestampStr) {
        this.totalMaintenanceTime = totalMaintenanceTime;
        this.totalDevelopmentTime = totalDevelopmentTime;
        this.times = times;
        this.saveTime = saveTime;
        this.roi = roi == 0 ? "0" : String.valueOf(roi);
        this.executionType = executionType;
        this.chartDate = timestampStr;
    }
}
