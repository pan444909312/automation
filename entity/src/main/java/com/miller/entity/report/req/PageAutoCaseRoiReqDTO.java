package com.miller.entity.report.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author panjuxiang
 * @since 2024/9/29 11:26
 */
@Data
public class PageAutoCaseRoiReqDTO {

    @Schema(description = "分页页码")
    private int pageNo = 1;
    @Schema(description = "分页大小")
    private int pageSize = 10;

    @Schema(description = "场景名称或id")
    private String scenarioIdOrName;

    @Schema(description = "执行人员名称")
    private String executionUser;

    @Schema(description = "创建开始时间")
    private String createStartTime;

    @Schema(description = "创建结束时间")
    private String createEndTime;

    @Schema(description = "更新开始时间")
    private String updateStartTime;

    @Schema(description = "更新结束时间")
    private String updateEndTime;

    @Schema(description = "名称是否可以重复，0：不可以 ，1：可以")
    private Integer isRepeat = 1;

    @Schema(description = "排序字段，1：场景id ，2：场景名称 ，3：开发成本 ，4：维护成本 ，5：单次节省成本，6：场景roi，7：创建时间")
    private Integer sort = 7;

    @Schema(description = "排序规则，0：正序 ，1：逆序")
    private Integer orderBy = 1;
}
