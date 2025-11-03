package com.miller.entity.report.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/9/26 9:30
 */
@Data
public class UiAutoCaseRoiReqDTO extends AutoCaseRoiReqDTO{

    @Schema(description = "执行策略 0:未知策略1:日常巡检;2:质量保证;3:效率提升")
    @TableField("execution_type")
    private Integer executionType;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    @TableField("execution_status")
    private Integer executionStatus;

}
