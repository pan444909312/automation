package com.miller.entity.apifox.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ApifoxRunResultDTO {

    private Integer successCount = 0;

    private Integer failCount = 0;

    private Set<ApifoxReportItemDTO> failList = null;
    private Set<ApifoxReportItemDTO> totalCaseList = null;

    private Set<String> successList = null;


    private Integer stepTotal=0;

    private Integer failStepCount=0;

    private Integer passStepCount=0;

    private Set<String> scenarioNameList = null;

    public void plusSuccessCount(int count){
        this.successCount += count;
    }

    public void plusFailCount(int count){
        this.failCount += count;
    }

    public void plusStepTotal(int count){
        this.stepTotal += count;
    }

    public void plusPassStepCount(int count){
        this.passStepCount += count;
    }

    public void plusFailStepCount(int count){
        this.failStepCount += count;
    }

    public void setScenarioNameList(String scenarioName) {
        if (ObjectUtils.isEmpty(scenarioNameList)) {
            scenarioNameList = new HashSet<>();
        }
        this.scenarioNameList.add(scenarioName);
    }

    public void setSuccessList(String scenarioName) {
        if (ObjectUtils.isEmpty(this.successList)) {
            this.successList = new HashSet<>();
        }
        this.successList.add(scenarioName);
    }

    public void setFailList(ApifoxReportItemDTO itemDTO) {
        if (ObjectUtils.isEmpty(this.failList)) {
            this.failList = new HashSet<>();
        }
        this.failList.add(itemDTO);
    }

    public void setTotalCaseList(ApifoxReportItemDTO caseInfoDTO) {
        if (ObjectUtils.isEmpty(this.totalCaseList)) {
            this.totalCaseList = new HashSet<>();
        }
        this.totalCaseList.add(caseInfoDTO);
    }
}
