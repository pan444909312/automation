package com.miller.entity.platform.req;

import lombok.Data;

import java.util.List;

/**
 * @Author: panjuxiang
 * @Since: 2025/8/4
 */
@Data
public class TestCaseRunScenarioReq {

    private List<String> packageNameList;
}
