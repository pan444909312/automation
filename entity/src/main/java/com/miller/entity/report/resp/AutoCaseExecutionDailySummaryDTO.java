package com.miller.entity.report.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: panjuxiang
 * @Since: 2025/8/19
 */
@Data
public class AutoCaseExecutionDailySummaryDTO {
    @Schema(description = "用例负责人，邮箱地址")
    @TableField("author")
    private String author;

    @Schema(description = "个数")
    private int count;
}
