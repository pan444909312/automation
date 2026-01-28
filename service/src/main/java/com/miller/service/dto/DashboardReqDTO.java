package com.miller.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DashboardReqDTO {

    private String startTime;

    private String endTime;

    private List<Long> platforms;

    private List<Long> projectIds;

    private List<String> emails;

}
