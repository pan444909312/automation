package com.miller.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/10/10 21:13
 */
@Data
public class PageAutoCaseExecutionRecordDTO {

    @Schema(description = "分页页码")
    private int pageNo = 1;
    @Schema(description = "分页大小")
    private int pageSize = 10;

    @Schema(description = "场景id")
    private String scenarioId;
}
