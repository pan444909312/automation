package com.miller.entity.tools;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.beans.Transient;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@TableName("tool_efficiency_stats")
@Data
@Accessors(chain = true)
public class ToolEfficiencyStatsEntity {

    /** 主键ID，自增 */
    private Long id;

    /** 工具名称 */
    private String toolName;

    /** 工具唯一 code 编码 */
    private    String toolCode;

    /** 实现人 */
    private String implementer;

    /** 实现时间 */
    private String implementationDate;

    /** 单次执行提效时间(分钟) */
    private Integer timeSavedPerExecution;

    /** 已执行次数 */
    private Integer executionCount = 0;

    /** 开发耗时(分钟) */
    private Integer developmentDuration;

    /** 投资回报率：(单次提效时间 × 执行次数) / 开发耗时 */
    private BigDecimal roi =BigDecimal.ZERO;

    /** 记录创建时间 */
    private Date createdAt;

    /** 记录更新时间 */
    private Date updatedAt;

    /** 状态：1-有效，0-无效/下线 */
    private Integer status = 1;

    /** 工具描述或备注 */
    private String description;

    private List<ToolExecutionStatisticsResultEntity> executionStatisticsResultList;
    
}
