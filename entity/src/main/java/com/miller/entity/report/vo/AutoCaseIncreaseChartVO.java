package com.miller.entity.report.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author panjuxiang
 * @since 2024/10/17 22:18
 */
@Data
public class AutoCaseIncreaseChartVO {

    @Schema(description = "新增用例数")
    @TableField("increase_case")
    private Integer increaseCase;

    @Schema(description = "备注")
    @TableField("remarks")
    private String remarks;

    @Schema(description = "日期")
    @TableField("create_time")
    private String date;


}
