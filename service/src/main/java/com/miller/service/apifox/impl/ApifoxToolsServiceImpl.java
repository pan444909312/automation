package com.miller.service.apifox.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.apifox.ApiFoxRunErrorSceneEntity;
import com.miller.entity.apifox.ApiFoxRunReportEntity;
import com.miller.entity.platform.User;
import com.miller.entity.report.req.ApifoxReportItemDTO;
import com.miller.entity.report.req.ApifoxRunResultDTO;
import com.miller.mapper.apifox.ApiFoxRunReportMapper;
import com.miller.service.apifox.ApiFoxRunErrorSceneService;
import com.miller.service.apifox.ApiFoxRunReportService;
import com.miller.service.apifox.ApifoxToolsService;
import com.miller.service.apifox.enums.ApiFoxCommonEnum;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import com.miller.service.framework.db.DBUtils;
import com.miller.service.framework.notification.dingtalk.DingTalkUtils;
import com.miller.service.platform.UserService;
import com.miller.service.util.PatternUtils;
import com.miller.service.util.SignGenerateUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ApifoxToolsServiceImpl implements ApifoxToolsService {

    private String WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send";


    @Autowired
    private UserService userService;

    @Autowired
    private ApiFoxRunReportService apiFoxRunReportService;

    @Autowired
    private ApiFoxRunErrorSceneService apiFoxRunErrorSceneService;

    /**
     * 一次性代码，apifox 钉钉通知二次包装，实现 @ 指定人
     */
    @Override
    public boolean sendDingDing(String accessToken, String timestamp, String sign, String body) {
        JSONObject jsonBody = JSONObject.parseObject(body);

        // 透传
        DingTalkClient client = new DefaultDingTalkClient(
                WEBHOOK_URL = WEBHOOK_URL
                        .concat("?access_token=").concat(accessToken)
                        .concat("&timestamp=").concat(String.valueOf(timestamp))
                        .concat("&sign=").concat(sign)
        );

        OapiRobotSendRequest req = new OapiRobotSendRequest();
        req.setMsgtype(jsonBody.getString("msgtype"));

        OapiRobotSendRequest.Actioncard actioncard = new OapiRobotSendRequest.Actioncard();
        JSONObject actionCardJson = jsonBody.getJSONObject("actionCard");
        actioncard.setTitle(actionCardJson.getString("title"));
        actioncard.setText(actionCardJson.getString("text"));
        req.setActionCard(actioncard);


        List<OapiRobotSendRequest.Btns> btns = new LinkedList<>();
        JSONArray btnsJson = actionCardJson.getJSONArray("btns");
        JSONObject btnJson = btnsJson.getJSONObject(0);
        OapiRobotSendRequest.Btns btn = new OapiRobotSendRequest.Btns();
        btn.setTitle(btnJson.getString("title"));
        String actionURL = btnJson.getString("actionURL");
        btn.setActionURL(actionURL);
        btns.add(btn);
        actioncard.setBtns(btns);
        req.setActionCard(actioncard);

        try {
            client.execute(req);
        } catch (ApiException e) {
            log.error(e.getErrMsg());
            return false;
        }

        req.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("请及时查看执行结果：".concat(actionURL));
        req.setText(text);

        // Text类型可用：定义钉钉通知到指定成员：
        // 有执行报错才会通知
        String messageContext = actionCardJson.getString("text");
        if (!messageContext.contains("失败数：  <font color=\"#FF0000\">0</font> 个")) {

            // 正则匹配执行人，查找不到默认为：王晓皓
            Pattern compile = Pattern.compile("测试人：QA_(.*?) ");
            Matcher matcher = compile.matcher(messageContext);
            String name = "王晓皓";
            if (matcher.find()) {
                String group = matcher.group(1);
                name = group.split("_")[1];
            }

            if (ObjectUtils.isEmpty(name)) return false;

            User userByName = userService.getUserByName(name);
            if (ObjectUtils.isEmpty(userByName)) return false;

            // 设置 @人
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(List.of(userByName.getMobile()));
            req.setAt(at);

            try {
                client.execute(req);
            } catch (ApiException e) {
                log.error(e.getErrMsg());
                return false;
            }
        }
        return true;
    }

    @Override
    public void parsingReport(AttributionGroupEnum attributionGroup) {
        // 读取apifox-reports文件夹下的最新json文件
        JSONObject apifoxReportJson = readApifoxReport(attributionGroup.name());


        // 一、 解析json文件 collection - item[0] - item（数组）遍历
        JSONObject collectionList = apifoxReportJson.getJSONObject("collection");
        if (ObjectUtils.isEmpty(collectionList)) {
            throw new RuntimeException("json 格式异常，无法提取到 Key: collection");
        }

        JSONObject itemJson = collectionList.getJSONArray("item").getJSONObject(0);
        if (ObjectUtils.isEmpty(itemJson)) {
            throw new RuntimeException("json 格式异常，无法提取到 Key: collection -> item");
        }

        JSONArray itemArray = itemJson.getJSONArray("item");
        Map<String, ApifoxReportItemDTO> reportItemDTOMap = new LinkedHashMap<>();
        for (Object item : itemArray) {
            JSONObject itemObj = (JSONObject) item;
            String type = itemObj.getString("type");

            if ("group".equals(type)) continue;


            JSONObject metaInfoObj = itemObj.getJSONObject("metaInfo");

            final String caseId = metaInfoObj.getString("relatedId");
            ApifoxReportItemDTO reportItem = null;
            if (reportItemDTOMap.containsKey(caseId)) {
                reportItem = reportItemDTOMap.get(caseId);
            } else {
                reportItem = new ApifoxReportItemDTO();
                reportItem.setCaseId(caseId);
            }


            final String httpApiId = metaInfoObj.getString("httpApiId");
            reportItem.addApiId(httpApiId);

            final String path = metaInfoObj.getString("httpApiPath");
            final String apifoxSavePath = "/api/automation/autoCaseRoi/apifox/save";
            if (StringUtils.isNotEmpty(path) && path.contains(apifoxSavePath)) {
                JSONObject requestObj = itemObj.getJSONObject("request");
                String body = requestObj.getJSONObject("body").getString("raw");
                //  正则匹配 scenarioId、scenarioName、负责人
                final String scenarioId = PatternUtils.matcher(body, "\"scenarioId\": \"(.*?)\"|\"scenarioId\": \"(.*?)\"");
                reportItem.setScenarioId(scenarioId);

                final String scenarioName = PatternUtils.matcher(body, "\"scenarioName\": \"(.*?)\"|\"scenarioName\": \"(.*?)\"");
                reportItem.setScenarioName(scenarioName);

                String executionUser = PatternUtils.matcher(body, "\"executionUser\": \"(.*?)\"|\"executionUser\": \"(.*?)\"");
                final String author = PatternUtils.matcher(body, "\"author\": \"(.*?)\"|\"author\": \"(.*?)\"");
                executionUser = (executionUser.isEmpty() || "".equals(executionUser)) ? author : executionUser;
                reportItem.setPersonInCharge(executionUser);
            }

            reportItemDTOMap.put(reportItem.getCaseId(), reportItem);

        }

        // 二、获取失败数据，并case运行结果为：false
        JSONArray failures = apifoxReportJson.getJSONObject("run").getJSONArray("failures");
        if (ObjectUtils.isNotEmpty(failures) && failures.size() > 0) {
            for (Object failure : failures) {
                JSONObject failureObj = (JSONObject) failure;
                JSONObject sourceObj = failureObj.getJSONObject("source");
                JSONObject metaInfo = sourceObj.getJSONObject("metaInfo");
                JSONObject errorObj = failureObj.getJSONObject("error");
                String relatedId = metaInfo.getString("relatedId");
                ApifoxReportItemDTO apifoxReportItemDTO = reportItemDTOMap.get(relatedId);
                apifoxReportItemDTO.setRunStatus(false);

                String stepName =  metaInfo.getString("httpApiName");
                apifoxReportItemDTO.addFailStep(null,stepName,errorObj);
                final String apiFoxUrl = String.format("https://apifox.hungrypanda.it/link/project/%s/api-test/scenario-%s", ApiFoxCommonEnum.APIFOX_PROJECT_ID.getValue(), relatedId);
                apifoxReportItemDTO.setApifoxUrl(apiFoxUrl);
                reportItemDTOMap.put(relatedId, apifoxReportItemDTO);
            }
        }

        // 三、区分每个负责人的成功数量、失败数量
        Map<String, ApifoxRunResultDTO> personCaseDataMap = new LinkedHashMap<>();
        reportItemDTOMap.forEach((key, value) -> {
            String personInCharge = value.getPersonInCharge();
            Boolean runStatus = value.getRunStatus();

            ApifoxRunResultDTO runResultObj = personCaseDataMap.containsKey(personInCharge) ?
                    personCaseDataMap.get(personInCharge) : new ApifoxRunResultDTO();


            final String scenarioName = value.getScenarioName();
            runResultObj.setScenarioNameList(scenarioName);
            runResultObj.plusStepTotal(value.getHttpApiIds().size());
            if (runStatus) {
                runResultObj.plusSuccessCount(1);
                runResultObj.plusPassStepCount(value.getHttpApiIds().size() );
                runResultObj.plusFailStepCount(0);
                runResultObj.setSuccessList(scenarioName);
            } else {
                runResultObj.plusFailCount(1);
                runResultObj.plusPassStepCount(value.getHttpApiIds().size() - value.getFailStepInfoList().size() );
                runResultObj.plusFailStepCount(value.getFailStepInfoList().size());
                runResultObj.setFailList(value);
            }
            personCaseDataMap.put(personInCharge, runResultObj);
        });


        // 运行报告落库
        final String runId = ULIDUtils.generateULID();
        personCaseDataMap.forEach((name, runResultObj) -> {

            ApiFoxRunReportEntity apiFoxRunReportEntity = apiFoxRunReportService.converToEntity(runId, name, attributionGroup, runResultObj);
            if (ObjectUtils.isNotEmpty(name) && name.length() > 0) {
                final Long id = apiFoxRunReportService.saveFindId(apiFoxRunReportEntity);

                if (ObjectUtils.isNotEmpty(runResultObj.getFailList())) {
                    runResultObj.getFailList().forEach((failItem) -> {
                        if (ObjectUtils.isNotEmpty(failItem)) {
                            ApiFoxRunErrorSceneEntity apiFoxRunErrorSceneEntity = new ApiFoxRunErrorSceneEntity();
                            apiFoxRunErrorSceneEntity.setReportId(id)
                                    .setScenarioName(failItem.getScenarioName())
                                    .setResponsiblePerson(name)
                                    .setRunResult(ApiFoxRunErrorSceneEntity.RunResult.ERROR)
                                    .setStepErrorInfo(JSONArray.toJSONString(failItem.getFailStepInfoList()))
                                    .setApifoxUrl(failItem.getApifoxUrl());
                            ;
                            apiFoxRunErrorSceneService.save(apiFoxRunErrorSceneEntity);
                        }

                    });
                }
            }
        });

        // 结果数据，发送钉钉消息
        StringBuffer msg = new StringBuffer();
        msg.append("## ").append(attributionGroup).append("组-各成员自动化执行结果通知：  \n\n  ");
        personCaseDataMap.forEach((name, runResultDTO) -> {

            if (StringUtils.isNotEmpty(name) && !"".equals(name)) {

                Integer successCount = runResultDTO.getSuccessCount();
                Integer failCount = runResultDTO.getFailCount();
                Integer totalCount = successCount + failCount;
                DecimalFormat df = new DecimalFormat("#.00");

                String successRate = "0.00";
                if (successCount > 0) {
                    successRate = df.format((double) successCount / totalCount * 100);
                }

                String failRate = "0.00";
                if (failCount > 0) {
                    failRate = df.format((double) failCount / totalCount * 100);
                }


                msg.append(" **").append(name).append(":**  \n\n  ")
                        .append("-  **TotalCount:** ").append(totalCount).append("  \n\n  ")
                        .append("-   **Fail: <font color=red>").append(runResultDTO.getFailCount()).append("</font>**   \n\n  ")
                        .append("-  Success: ").append(runResultDTO.getSuccessCount()).append("  \n\n  ")
                        .append("-  **FailRate: <font color=red>").append(failRate).append("% </font>**    \n\n  ")
                        .append("-  **SuccessRate: <font color=green>").append(successRate).append("% </font>** \n\n  ")

                ;
            }
        });
        msg.append("> **ApiFox_每日定时运行报告:[点击查看](http://47.242.73.37:2080/app/application/apifox-68db96e1b752566623a315fa)**  \n  ")
        ;


        log.info("Apifox每日运行报告推送钉钉: 成功");
        log.info(msg.toString());
        // TODO 调试完成后恢复
        // 推送钉钉群消息
        DingTalkUtils.sendMarkdownMessage(
                "各成员自动化执行结果通知:",
                msg.toString(),
                "121a18c07ba54967e437533ea2492e8dd25b6af0448140e487b703412f6574b1",
                "SEC59acb673a2582ff2546c73be8694083cce839cd2eb1293cd062b24f0a0a73a67");


    }


//    /**
//     * 查询用例名称
//     */
//    public String queryCaseName(String caseId) {
//        DBUtils dbUtils = new DBUtils().initApifoxDB();
//        return dbUtils.queryOneObjectReturnObject("select name from api_test_cases  where id= ? ", String.class, caseId);
//    };


    public static JSONObject readApifoxReport(String containsStr) {
        // 获取apifox-reports文件夹路径
        String reportsPath = "apifox-reports";
        File reportsDir = new File(reportsPath);
        JSONObject reportJson = new JSONObject();

        // 成功率 & 失败率
        double successRate = 0.0;
        double failRate = 0.0;


        // 检查目录是否存在
        if (!reportsDir.exists() || !reportsDir.isDirectory()) {
            log.error("apifox-reports文件夹不存在");
            return reportJson;
        }

        // 获取所有json文件
        File[] jsonFiles = reportsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".json") && name.contains(containsStr));

        if (jsonFiles == null || jsonFiles.length == 0) {

            log.error("未找到json文件");
            throw new RuntimeException("未找到json文件");
        }

        // 按最后修改时间排序，获取最新的json文件
        File latestFile = Arrays.stream(jsonFiles)
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);

        if (latestFile == null) {
            log.error("无法获取最新的json文件");
            return reportJson;
        }

        try {
            // 读取json文件内容
            String jsonContent = new String(Files.readAllBytes(latestFile.toPath()));

            // 创建json实例
            reportJson = JSONObject.parseObject(jsonContent);

            // TODO: 后续处理json数据
            log.info("成功解析最新的测试报告: {}", latestFile.getName());

        } catch (IOException e) {
            log.error("读取json文件失败", e);
        } catch (Exception e) {
            log.error("解析json文件失败", e);
        }
        return reportJson;
    }


}
