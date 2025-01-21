package com.miller.entity.report.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author panjuxiang
 * @since 2024/10/31 21:36
 */
@Data
public class PageAutoCaseRoiChartReqDTO {
    @Schema(description = "分页页码")
    private int pageNo = 1;
    @Schema(description = "分页大小")
    private int pageSize = 10;

    @Schema(description = "创建开始时间")
    private String createStartTime;

    @Schema(description = "创建结束时间")
    private String createEndTime;

    @Schema(description = "执行策略 0:未知策略 1:日常巡检;2:质量保证;3:效率提升")
//    @NotNull(message = "执行策略不能为空")
    private List<Integer> executionTypeList;


}
