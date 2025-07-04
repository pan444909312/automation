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


    @Schema(description = "ID")
    private Long id = System.currentTimeMillis();

    @Schema(description = "累计维护成本")
    private Long totalMaintenanceTime;

    @Schema(description = "累计开发成本")
    private Long totalDevelopmentTime;

    @Schema(description = "累计执行次数")
    private Integer times;

    @Schema(description = "累计收益")
    private Long saveTime;

    @Schema(description = "场景ROI")
    private String roi;

//    @Schema(description = "执行策略 0:未知策略 1:日常巡检;2:质量保证;3:效率提升")
//    @TableField("execution_type")
//    private Integer executionType;

    @Schema(description = "节省人日")
    private Double savePersonDay;

    @Schema(description = "日期")
    private String createTime;

}
