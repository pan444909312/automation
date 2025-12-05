package com.miller.entity.apifox.DTO;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class ApifoxReportItemDTO {

    private String caseId;

    private Set<String> httpApiIds;

    private String scenarioId;

    private String scenarioName;

    private Boolean runStatus = true;

    private String personInCharge;

    private int assertCount;


    private List<ApiFoxRespStepInfoDTO> failStepInfoList;
    private List<ApiFoxRespStepInfoDTO> stepInfoList;

    private String apifoxUrl;


    public void addAssertCount(int count) {
        this.assertCount += count;
    }

    public void addStep(ApiFoxRespStepInfoDTO stepInfoDTO) {
        if (ObjectUtils.isEmpty(stepInfoList)) {
            stepInfoList = new ArrayList<>();
        }

        Set<String> stepIds = stepInfoList.stream()
                .map(ApiFoxRespStepInfoDTO::getStepId)
                .collect(Collectors.toSet());

        if (stepIds.contains(stepInfoDTO.getStepId())) {
            return;
        }
        stepInfoList.add(stepInfoDTO);
    }

    public void addFailStep(String id, String name, Set<JSONObject> failureList) {
        if (ObjectUtils.isEmpty(failStepInfoList)) {
            failStepInfoList = new ArrayList<>();
        }
        ApiFoxRespStepInfoDTO apiFoxFailStepInfoDTO = new ApiFoxRespStepInfoDTO(id, name, failureList);
        failStepInfoList.add(apiFoxFailStepInfoDTO);
    }

    public void addFailStep(ApiFoxRespStepInfoDTO apiFoxFailStepInfoDTO) {
        if (ObjectUtils.isEmpty(failStepInfoList)) {
            failStepInfoList = new ArrayList<>();
        }
        failStepInfoList.add(apiFoxFailStepInfoDTO);
    }


    public void addApiId(String apiId) {
        if (ObjectUtils.isEmpty(this.httpApiIds)) {
            this.httpApiIds = new HashSet<>();
        }
        this.httpApiIds.add(apiId);
    }

}