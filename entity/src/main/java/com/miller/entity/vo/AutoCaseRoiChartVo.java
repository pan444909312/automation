package com.miller.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/10/31 21:34
 */
@Data
public class AutoCaseRoiChartVo {


    @Schema(description = "ID 自增")
    private Long id;

    @Schema(description = "成本")
    private Long costTime;

    @Schema(description = "投产次数")
    private Integer times;

    @Schema(description = "收益")
    private Long saveTime;

    @Schema(description = "场景ROI")
    private String roi;

    @Schema(description = "执行策略 0:所有策略 1:日常巡检;2:质量保证;3:效率提升")
    private String executionType;

    @Schema(description = "节省人日")
    private Double savePersonDay;

    @Schema(description = "日期")
    private String createTime;

}
