package com.miller.entity.report.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 查询最近执行记录请求DTO
 *
 * @author panjuxiang
 */
@Data
public class ListRecentExecutionRecordReqDTO {

    @Schema(description = "用例归属平台类型列表（1：JAVA 2：APIFOX 3：JMETER 4：UI自动化）")
    private List<Integer> platformTypeList;

    @Schema(description = "关联项目id列表")
    private List<String> projectIdList;

    @Schema(description = "开始时间，格式：yyyy-MM-dd")
    private String startTime;

    @Schema(description = "结束时间，格式：yyyy-MM-dd")
    private String endTime;

    @Schema(description = "用例负责人列表")
    private List<String> authorList;
}
