package com.miller.entity.report.req;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;

@Data
@Accessors(chain = true)
public class ApifoxReportItemDTO {

    private String caseId;
    
    private Set<String> httpApiIds;

    private String scenarioId;

    private String scenarioName;

    private Boolean runStatus = true;

    private String personInCharge;

    private List<ApiFoxFailStepInfoDTO> failStepInfoList;

    private String apifoxUrl;

    public void addFailStep(String id , String name , JSONObject failureObjStr ){
        if(ObjectUtils.isEmpty(failStepInfoList)){
            failStepInfoList =  new ArrayList<>();
        }
        ApiFoxFailStepInfoDTO apiFoxFailStepInfoDTO = new ApiFoxFailStepInfoDTO(id,name,failureObjStr);
        failStepInfoList.add(apiFoxFailStepInfoDTO);
    }

    public void addApiId(String apiId) {
        if (ObjectUtils.isEmpty(this.httpApiIds)){
            this.httpApiIds = new HashSet<>();
        }
        this.httpApiIds.add(apiId);
    }

}