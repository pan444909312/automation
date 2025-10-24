package com.miller.entity.report.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: panjuxiang
 * @Since: 2025/8/19
 */
@Data
public class AutoCaseExecutionDailyDataDTO {
    @Schema(description = "用例负责人，邮箱地址")
    @TableField("author")
    private String author;

    @Schema(description = "总个数")
    private int count;

    @Schema(description = "成功个数")
    private int successCount;

    @Schema(description = "失败个数")
    private int failCount;

    @Schema(description = "通过率")
    private double passRate;

    @Schema(description = "项目Id")
    private int projectId;
}
