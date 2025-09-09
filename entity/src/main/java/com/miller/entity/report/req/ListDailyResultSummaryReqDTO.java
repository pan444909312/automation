package com.miller.entity.report.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @Author: panjuxiang
 * @Since: 2025/9/8
 */

@Data
public class ListDailyResultSummaryReqDTO {

    @Schema(description = "用例作者")
    private String author;

    @Schema(description = "日期")
    private String date;

    @Schema(description = "关联项目id")
    private String projectId;

}
