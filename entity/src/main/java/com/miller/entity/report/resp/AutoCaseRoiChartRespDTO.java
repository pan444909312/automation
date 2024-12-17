package com.miller.entity.report.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/10/31 21:34
 */
@Data
public class AutoCaseRoiChartRespDTO {


    @Schema(description = "ID 自增")
    private Long id;

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

    @Schema(description = "节省人日")
    private Double savePersonDay;

    @Schema(description = "日期")
    private String createTime;

}
