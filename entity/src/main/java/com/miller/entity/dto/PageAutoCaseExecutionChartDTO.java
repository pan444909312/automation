package com.miller.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author panjuxiang
 * @since 2024/10/17 22:10
 */
@Data
public class PageAutoCaseExecutionChartDTO {

    @Schema(description = "分页页码")
    private int pageNo = 1;
    @Schema(description = "分页大小")
    private int pageSize = 10;

    @Schema(description = "创建开始时间")
    private Date createStartTime;

    @Schema(description = "创建结束时间")
    private Date createEndTime;

}
