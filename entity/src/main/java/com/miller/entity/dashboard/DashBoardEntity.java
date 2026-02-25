package com.miller.entity.dashboard;

import com.miller.entity.report.AutoCaseRoiEntity;
import lombok.Data;

@Data
public class DashBoardEntity extends AutoCaseRoiEntity {

    private Integer rangeTimeExecCount;

    private Integer successCount;

    private Integer failureCount;

}
