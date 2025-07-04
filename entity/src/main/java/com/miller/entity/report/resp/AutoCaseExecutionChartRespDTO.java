package com.miller.entity.report.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author panjuxiang
 * @since 2024/10/17 22:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoCaseExecutionChartRespDTO {

    @Schema(description = "执行用例数")
    private Integer executionCase;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "日期")
    private String date;

}
