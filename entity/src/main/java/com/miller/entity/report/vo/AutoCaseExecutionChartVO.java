package com.miller.entity.report.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author panjuxiang
 * @since 2024/10/17 22:16
 */
@Data
public class AutoCaseExecutionChartVO {

    @Schema(description = "执行用例数")
    @TableField("execution_case")
    private Integer executionCase;

    @Schema(description = "备注")
    @TableField("remarks")
    private String remarks;

    @Schema(description = "日期")
    @TableField("create_time")
    private String date;

}
