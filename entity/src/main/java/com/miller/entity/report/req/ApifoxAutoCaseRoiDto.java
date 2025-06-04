package com.miller.entity.report.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApifoxAutoCaseRoiDto {


    @Schema(description = "场景id")
    private String scenarioId;

    @Schema(description = "场景名称")
    private String scenarioName;

    @Schema(description = "执行策略 1:日常巡检;2:质量保证;3:效率提升")
    private Integer executionType;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    private Integer executionStatus;

    @Schema(description = "开发成本")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    private Integer manualTestTime;

    @Schema(description = "执行人员名称")
    private String executionUser;

    @Schema(description = "实现日期")
    private String createTime;

    @Schema(description = "初始化执行次数")
    private Integer times;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "负责人")
    private String author;

    @Schema(description = "归属小组")
    @NotBlank(message = "归属小组不能为空，枚举值有：B-商家组、P-平台组、C-导购组、D-配送组")
    private String dept;

    private String  projectId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "预期测试用例被执行的次数")
    private Integer expectTimes;

    @Schema(description = "备注信息")
    private String remark;

}
