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

    @Schema(description = "用例归属平台类型（1：JAVA 2：APIFOX 3：JMETER 4：UI自动化")
    private int platformType;
}
