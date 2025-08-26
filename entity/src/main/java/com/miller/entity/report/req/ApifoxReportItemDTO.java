package com.miller.entity.report.req;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ApifoxReportItemDTO {

    private String caseId;
    
    private Set<String> httpApiIds;

    private String scenarioId;

    private String scenarioName;

    private Boolean runStatus = true;

    private String personInCharge;


    public void addApiId(String apiId) {
        if (ObjectUtils.isEmpty(this.httpApiIds)){
            this.httpApiIds = new HashSet<>();
        }
        this.httpApiIds.add(apiId);
    }

}