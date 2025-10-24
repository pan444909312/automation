package com.miller.entity.tools;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tool_execution_log")
@Data
@Accessors(chain = true)
public class ToolExecutionLogEntity {


    /** 主键ID，自增 */
    private Long id;

    /** 外键，关联工具ID */
    private Long toolId;

    /** 本次执行的操作人 */
    private String executor;

    /** 执行状态：成功/失败/已中止 */
    private String executionStatus;

    /** 本次执行输入的参数或配置 */
    private String inputParameters;

    /**
     * 每次节省时间（分钟）
     */
    private Integer timeSavedPerExecution;

    /** 执行的输出结果或错误信息 */
    private String outputResult;
    
    /** 日志记录时间 */
    private java.time.LocalDateTime createdAt;

}