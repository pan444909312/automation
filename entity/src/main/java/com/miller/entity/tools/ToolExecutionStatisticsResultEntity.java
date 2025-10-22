package com.miller.entity.tools;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ToolExecutionStatisticsResultEntity {


    private String executor;

    private Integer count;

    private Integer timeToImproveEfficiency;


}
