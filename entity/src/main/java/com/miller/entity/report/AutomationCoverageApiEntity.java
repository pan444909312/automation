package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("automation_coverage_api")
public class AutomationCoverageApiEntity {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String host;
    
    private String method;
    
    private String path;
    
    private Long requestsTimesProduction;
    
    private String country;
    
    private Integer isAutomation;

    private Integer lastExecuteTime;
    
    private String executor;
    
    private Integer isDelete;

    private String testCasePath;

    private String testCaseRequestLast;

    private String testCaseResponseLast;

}