package com.miller.service.platform.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.platform.User;
import com.miller.entity.platform.UserBindProject;
import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.mapper.report.AutomationCoverageApiMapper;
import com.miller.service.apifox.flow.ApifoxRequestFlow;
import com.miller.service.platform.AutomationCoverageApiService;
import com.miller.service.platform.UserBindProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class AutomationCoverageApiServiceImpl extends ServiceImpl<AutomationCoverageApiMapper, AutomationCoverageApiEntity> implements AutomationCoverageApiService {

    @Autowired
    AutomationCoverageApiMapper coverageApiMapper;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserBindProjectService userBindProjectService;

    Map<String, String> userBindProjectMap = new HashMap<>();

    @Override
    public Boolean updateCoverageApi(AutomationCoverageApiEntity entity) {


        // 提供http请求的方式调用
        List<AutomationCoverageApiEntity> entityList = coverageApiMapper.findByTestCaseRequestPath(entity.getTestCaseRequestPath());
        if (ObjectUtils.isEmpty(entityList)) {
            log.error("没有需要变更的活跃接口数据");
            return false;
        }
        List<Long> ids = entityList.stream().map(AutomationCoverageApiEntity::getId).toList();
        return this.batchUpdateCoverageApi(ids, entity);
    }

    @Override
    public Set<String> initApifoxCoverageApi() throws Exception {
        ApifoxRequestFlow apifoxRequestFlow = new ApifoxRequestFlow();
        JSONArray testScenarioTreeList = apifoxRequestFlow.getTestScenarioTreeList();
        log.info("执行场景数量".concat(String.valueOf(testScenarioTreeList.size())));
        Set<String> pathList = new HashSet<>();
        for (int i = 0; i < testScenarioTreeList.size(); i++) {
            JSONObject testCase = testScenarioTreeList.getJSONObject(i);
            final String name = testCase.getString("name");
            final Integer creatorId = testCase.getInteger("creatorId");
            final Integer id = testCase.getInteger("id");
            JSONArray caseSteps = apifoxRequestFlow.getCaseStep(id);
            log.info("场景名称：".concat(name));
            log.info("步骤数量：".concat(String.valueOf(caseSteps.size())));
            if (ObjectUtils.isNotEmpty(caseSteps) && caseSteps.size() > 0) {
                for (int j = 0; j < caseSteps.size(); j++) {
                    JSONObject stepObj = caseSteps.getJSONObject(j);
                    final String stepType = stepObj.getString("type");
                    log.info("步骤类型：".concat(stepType));
                    // 步骤类型为：testCaseRef -  引用场景， group - 步骤组，则直接跳过
                    if (stepType.equals("testCaseRef") || stepType.equals("group") || stepType.equals("if") || stepType.equals("database") || stepType.equals("delay")) {
                        continue;
                    }
                    JSONObject customHttpRequest = stepObj.getJSONObject("customHttpRequest");
                    if (ObjectUtils.isEmpty(customHttpRequest)) {
                        continue;
                    }

                    String path = this.formatPath(customHttpRequest.getString("path"));
                    // 已经处理过的数据，直接跳过
                    if (pathList.contains(path)) {
                        continue;
                    }
                    final String stepName = customHttpRequest.getString("name");
                    final String method = customHttpRequest.getString("method").toUpperCase();

                    final String requestBody = customHttpRequest.getJSONObject("requestBody").getString("data");
                    String headers = "{}";
                    if (customHttpRequest.containsKey("parameters")) {
                        JSONObject parameters = customHttpRequest.getJSONObject("parameters");
                        if (!ObjectUtils.isEmpty(parameters)) {
                            if (parameters.containsKey("header")) {
                                headers = parameters.getJSONArray("header").toJSONString();
                            }
                        }

                    }
                    // 获取成员信息
                    String userNick = apifoxRequestFlow.getUserNick(creatorId);
                    String projectId = "";
                    if (userBindProjectMap.containsKey(userNick)) {
                        projectId = userBindProjectMap.get(userNick);
                    } else {
                        User user = userService.getUserByName(userNick);
                        if (ObjectUtils.isNotEmpty(user)) {
                            UserBindProject userBindProject = userBindProjectService.selectByUserId(user.getUserId());
                            projectId = userBindProject.getProjectId();
                        }
                        userBindProjectMap.put(userNick, projectId);
                    }


                    AutomationCoverageApiEntity automationCoverageApiEntity = new AutomationCoverageApiEntity();
                    automationCoverageApiEntity
                            .setPath(path)
                            .setTestCaseRequestPath(path)
                            .setMethod(method)
                            .setTestCaseRequestMethod(method)
                            .setApiTestAuthor(userNick)
                            .setTestCaseRequestBody(requestBody)
                            .setTestCaseRequestHeaders(headers)
                            .setProjectId(projectId);
                    this.updateCoverageApi(automationCoverageApiEntity);
                    pathList.add(path);
                }
            }
        }

        return pathList;
    }


    /**
     * 批量更新活跃接口
     *
     * @param ids
     * @param entity
     * @return
     */
    public Boolean batchUpdateCoverageApi(List<Long> ids, AutomationCoverageApiEntity entity) {
        UpdateWrapper<AutomationCoverageApiEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_automation", 1)
                .set("api_status", 0)
                .set("api_test_author", entity.getApiTestAuthor())
                .set("test_case_request_path", entity.getTestCaseRequestPath())
                .set("test_case_request_method", entity.getTestCaseRequestMethod())
                .set("test_case_request_headers", entity.getTestCaseRequestHeaders())
                .set("test_case_request_body", entity.getTestCaseRequestBody())
                .set("project_id", entity.getProjectId())
                .in("id", ids);

        return  this.update(updateWrapper);
    }

    /**
     * 接口格式处理
     */
    public  String  formatPath(String path) {
        // 处理此类URL数据 {{pos_url}}/api/pos/v1/open/menu/categories
        String[] paths = path.split("/api/");
        path = "/api/".concat(paths[paths.length - 1]);

        // 处理此类URL数据 /api/pos/v1/open/menu/categories?aaa=222
        path = path.split("\\?")[0];

        // 处理此类URL数据 /api/pos/v1/open/menu/{{pos_shop_id}}/categories
//        path = path.replaceAll("\\{\\{.*?\\}\\}","?");

        log.info("Path：".concat(path));
        return path;
    }


}