package com.miller.entity.report.req;

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

    private Set<String> scenarioNameList = null;

    public void setScenarioNameList(String scenarioName) {
        if (ObjectUtils.isEmpty(scenarioNameList)) {
            scenarioNameList = new HashSet<>();
        }
        this.scenarioNameList.add(scenarioName);
    }
}
