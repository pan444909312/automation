package com.miller.service.apifox.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.apifox.ApiFoxRunErrorSceneEntity;
import com.miller.entity.apifox.ApiFoxRunReportEntity;
import com.miller.entity.apifox.ApiTestCaseCustomHttpRequestEntity;
import com.miller.entity.apifox.DTO.ApiFoxRespStepInfoDTO;
import com.miller.entity.apifox.DTO.ApifoxReportItemDTO;
import com.miller.entity.apifox.DTO.ApifoxRespMetaInfoDTO;
import com.miller.entity.apifox.DTO.ApifoxRunResultDTO;
import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.report.AutoCaseDailyReport;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.mapper.apifox.ApiFoxRunReportMapper;
import com.miller.service.apifox.ApiFoxRunErrorSceneService;
import com.miller.service.apifox.ApiFoxRunReportService;
import com.miller.service.apifox.ApiTestCaseCustomHttpRequestService;
import com.miller.service.apifox.enums.ApiFoxCommonEnum;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import com.miller.service.framework.notification.dingtalk.DingTalkUtils;
import com.miller.service.platform.UserService;
import com.miller.service.report.AutoCaseDailyReportService;
import com.miller.service.report.AutoExecutionRecordService;
import com.miller.service.util.DateUtils;
import com.miller.service.util.PatternUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApiFoxRunReportServiceImpl extends ServiceImpl<ApiFoxRunReportMapper, ApiFoxRunReportEntity> implements ApiFoxRunReportService {

    private String WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send";

    @Autowired
    private ApiFoxRunErrorSceneService apiFoxRunErrorSceneService;


    @Autowired
    private UserService userService;

    @Autowired
    private AutoExecutionRecordService autoExecutionRecordService;

    @Autowired
    private ApiTestCaseCustomHttpRequestService apiTestCaseCustomHttpRequestService;

    @Autowired
    @Lazy
    private AutoCaseDailyReportService dailyReportService;


    @Override
    public ApiFoxRunReportEntity converToEntity(String runId, String name, AttributionGroupEnum group, ApifoxRunResultDTO dto) {
        if (StringUtils.isEmpty(name)) {
            name = "other";
        }

        final int totalRuns = dto.getFailCount() + dto.getSuccessCount();
        ApiFoxRunReportEntity apiFoxRunReportEntity = new ApiFoxRunReportEntity();
        apiFoxRunReportEntity
                .setRunId(runId)
                .setBelongingGroup(group.name())
                .setResponsiblePerson(name)
                .setSuccessRuns(dto.getSuccessCount())
                .setFailureRuns(dto.getFailCount())
                .setStepTotal(dto.getStepTotal())
                .setFailStepCount(dto.getFailStepCount())
                .setPassStepCount(dto.getPassStepCount())
                .setTotalRuns(totalRuns)
                .setSuccessRate((double) dto.getSuccessCount() / totalRuns)
                .setFailureRate((double) dto.getFailCount() / totalRuns)
        ;
        return apiFoxRunReportEntity;
    }

    @Override
    public Long saveFindId(ApiFoxRunReportEntity entity) {
        this.save(entity);

        /**
         * 根据 runId 和 负责人 查询唯一组件ID
         */
        QueryWrapper<ApiFoxRunReportEntity> apiFoxRunReportEntityQueryWrapper = new QueryWrapper<>();
        apiFoxRunReportEntityQueryWrapper.eq("run_id", entity.getRunId());
        apiFoxRunReportEntityQueryWrapper.eq("responsible_person", entity.getResponsiblePerson());
        ApiFoxRunReportEntity queryResult = this.getOne(apiFoxRunReportEntityQueryWrapper);
        return queryResult.getId();
//        return 1232L;
    }

    @Override
    public List<ApiFoxRunReportEntity> queryByRunId(String runId) {
        QueryWrapper<ApiFoxRunReportEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("run_id", runId);
        List<ApiFoxRunReportEntity> apiFoxRunReportEntities = this.baseMapper.selectList(queryWrapper);

        if (ObjectUtils.isEmpty(apiFoxRunReportEntities)) {
            apiFoxRunReportEntities = new ArrayList<ApiFoxRunReportEntity>();
        }

        return apiFoxRunReportEntities;
    }

    @Override
    public String queryBelongingGroup(Long apiFoxCaseId) {
        ApiFoxRunErrorSceneEntity errorSceneEntity = apiFoxRunErrorSceneService.findByCaseId(apiFoxCaseId);
        if (ObjectUtils.isEmpty(errorSceneEntity)) {
            log.error(String.format("caseId is null: %s", apiFoxCaseId));
            return "B";
        }
        Long reportId = errorSceneEntity.getReportId();
        ApiFoxRunReportEntity reportEntity = this.getById(reportId);

        return reportEntity.getBelongingGroup();

    }


    /**
     * 删除数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delReport(Long reportId, String remark, String optUser) {

        if (ObjectUtils.isEmpty(remark)) {
            throw new RuntimeException("请填写备注信息");
        }

        ApiFoxRunReportEntity reportEntity = this.getById(reportId);
        if (ObjectUtils.isEmpty(reportEntity)) {
            throw new RuntimeException(String.format("reportId is null: %s", reportId));
        }

        final String currentDate = DateUtils.getCurrentDate("yyyy-MM-dd");

        String finalRemarks = String.format("""
                %s \n
                操作人：%s \n
                操作时间：%s
                """, remark, optUser, currentDate);
        reportEntity.setRemark(finalRemarks);
        reportEntity.setIsDel(-1);
        this.saveOrUpdate(reportEntity);

        // 将当前报告下的所有步骤删除
        apiFoxRunErrorSceneService.updateToDel(reportId);

        AutoCaseDailyReport autoCaseDailyReport = new AutoCaseDailyReport()
                .setReportTag(1)
                .setRunDate(currentDate)
                .setAuthor(reportEntity.getResponsiblePerson())
                .setRemark(finalRemarks)
                ;

        dailyReportService.updateReportTag(autoCaseDailyReport);


    }


    /**
     * ApiFox 报告解析 & 落库
     */
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


        // 获取失败步骤数据
        Map<String, Set<JSONObject>> failureStepMap = this.getFailMetaInfoMap(apifoxReportJson);


        // key：Apifox 用例ID，  value：用例明细，用例步骤明细
        Map<String, ApifoxReportItemDTO> reportItemDTOMap = new LinkedHashMap<>();

        // 临时集合，主要用于集合查询使用
        int forCount = (int) Math.ceil((double) itemArray.size() / 50); // 计算总批次数
        int index = 0;
        for (int i = 0; i < forCount; i++) {
            // 原始数据：步骤信息
            List<JSONObject> temporaryItemList = new ArrayList<>();
            int count = 1;
            while (true) {
                JSONObject itemObj = (JSONObject) itemArray.get(index);
                temporaryItemList.add(itemObj);
                if (count == 50 || index + 1 == itemArray.size()) {
                    index++;
                    break;
                } else {
                    index++;
                    count++;
                }

            }


            // 获取 Apifox 原库 step 表数据
            Set<String> apifoxStepIds = temporaryItemList.stream().map(obj -> {
                ApifoxRespMetaInfoDTO metaInfo = obj.getObject("metaInfo", ApifoxRespMetaInfoDTO.class);
                return metaInfo.getHttpApiId();
            }).collect(Collectors.toSet());

            List<ApiTestCaseCustomHttpRequestEntity> apifoxStepList = apiTestCaseCustomHttpRequestService.queryByIdList(apifoxStepIds);

            // 处理步骤归属用例，并组装到 reportItemDTOMap 中
            for (JSONObject itemObj : temporaryItemList) {

                // 跳过不处理的步骤类型
                String type = itemObj.getString("type");
                if ("group".equals(type) || "delay".equals(type) || "database".equals(type)) continue;

                // 报告的步骤信息
                ApifoxRespMetaInfoDTO metaInfoObj = itemObj.getObject("metaInfo", ApifoxRespMetaInfoDTO.class);

                final String caseId = metaInfoObj.getRelatedId();
                ApifoxReportItemDTO reportItem = null;
                if (reportItemDTOMap.containsKey(caseId)) {
                    reportItem = reportItemDTOMap.get(caseId);
                } else {
                    reportItem = new ApifoxReportItemDTO();
                    reportItem.setCaseId(caseId);
                }

                final String httpApiId = metaInfoObj.getHttpApiId();
                reportItem.addApiId(httpApiId);


                // 配置跳转 ApiFox Case 地址
                final String apiFoxUrl = String.format("https://apifox.hungrypanda.it/link/project/%s/api-test/scenario-%s", ApiFoxCommonEnum.APIFOX_PROJECT_ID.getValue(), reportItem.getCaseId());
                reportItem.setApifoxUrl(apiFoxUrl);


                final String path = metaInfoObj.getHttpApiPath();
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


                // 组装 step 的数据
                ApiTestCaseCustomHttpRequestEntity httpRequestEntity = apifoxStepList
                        .stream()
                        .filter(obj -> String.valueOf(obj.getId()).equals(metaInfoObj.getHttpApiId()))
                        .findFirst()
                        .orElse(new ApiTestCaseCustomHttpRequestEntity());

                ApiFoxRespStepInfoDTO stepInfoDTO = ApiFoxRespStepInfoDTO.setStepInfo(metaInfoObj, httpRequestEntity);


                // 判断是否有断言失败的步骤
                Set<JSONObject> stepAssertErrorList = failureStepMap.get(stepInfoDTO.getStepId());
                if (ObjectUtils.isNotEmpty(stepAssertErrorList) && stepAssertErrorList.size() > 0) {
                    reportItem.setRunStatus(false);
                    stepInfoDTO.setFailureList(stepAssertErrorList);
                    stepInfoDTO.setRunStatus(false);
                    reportItem.addFailStep(stepInfoDTO);
                }


                reportItem.addStep(stepInfoDTO);

                reportItem.addAssertCount(stepInfoDTO.getAssertCount());

                reportItemDTOMap.put(reportItem.getCaseId(), reportItem);
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
                runResultObj.plusPassStepCount(value.getHttpApiIds().size());
                runResultObj.plusFailStepCount(0);
                runResultObj.setSuccessList(scenarioName);
            } else {
                runResultObj.plusFailCount(1);
                runResultObj.plusPassStepCount(value.getHttpApiIds().size() - value.getFailStepInfoList().size());
                runResultObj.plusFailStepCount(value.getFailStepInfoList().size());
                runResultObj.setFailList(value);
            }
            runResultObj.setTotalCaseList(value);
            personCaseDataMap.put(personInCharge, runResultObj);
        });


        // 运行报告落库
        final String runId = ULIDUtils.generateULID();
        personCaseDataMap.forEach((name, runResultObj) -> {

            ApiFoxRunReportEntity apiFoxRunReportEntity = this.converToEntity(runId, name, attributionGroup, runResultObj);
            if (ObjectUtils.isNotEmpty(name) && name.length() > 0) {
                // 按成员落库执行结果数据，并返回ID
                final Long id = this.saveFindId(apiFoxRunReportEntity);

                if (ObjectUtils.isNotEmpty(runResultObj.getTotalCaseList())) {
                    // 处理用例数据，并落库用例明细
                    runResultObj.getTotalCaseList().forEach((caseInfo) -> {
                        if (ObjectUtils.isNotEmpty(caseInfo)) {
                            ApiFoxRunErrorSceneEntity apiFoxRunErrorSceneEntity = new ApiFoxRunErrorSceneEntity();
                            apiFoxRunErrorSceneEntity.setReportId(id)
                                    .setScenarioName(caseInfo.getScenarioName())
                                    .setResponsiblePerson(name)
                                    .setStepInfoList(JSONArray.toJSONString(caseInfo.getStepInfoList()))
                                    .setStepErrorInfo(JSONArray.toJSONString(caseInfo.getFailStepInfoList()))
                                    .setApifoxUrl(caseInfo.getApifoxUrl())
                                    .setScenarioId(caseInfo.getScenarioId())
                            ;


                            // 计算用例校验点数量
                            final Integer caseAssertCount = caseInfo.getStepInfoList().stream().map(
                                    step -> {
                                        JSONArray postProcessors = step.getPostProcessors();
                                        List<Boolean> collect = postProcessors.stream()
                                                .map(p -> ((JSONObject) p).getString("type").equals("assertion"))
                                                .filter(res -> res.equals(true))
                                                .toList();
                                        return collect.size();
                                    }
                            ).mapToInt(Integer::intValue).sum();
                            apiFoxRunErrorSceneEntity.setAssertCount(caseAssertCount);
                            apiFoxRunErrorSceneEntity.setApifoxCaseId(caseInfo.getCaseId());


                            ApiFoxRunErrorSceneEntity.RunResult runResult = caseInfo.getRunStatus() ? ApiFoxRunErrorSceneEntity.RunResult.SUCCESS : ApiFoxRunErrorSceneEntity.RunResult.ERROR;
                            apiFoxRunErrorSceneEntity.setRunResult(runResult);
                            apiFoxRunErrorSceneService.save(apiFoxRunErrorSceneEntity);

                            // 更新执行记录表状态，更新该失败的用例最近的一条执行记录为失败
                            String scenarioId = caseInfo.getScenarioId();
                            if (ObjectUtils.isNotEmpty(scenarioId) && !caseInfo.getRunStatus()) {
                                UpdateWrapper<AutoExecutionRecordEntity> autoExecutionRecordEntityUpdateWrapper = new UpdateWrapper<>();
                                autoExecutionRecordEntityUpdateWrapper.eq("scenario_id", scenarioId);
                                autoExecutionRecordEntityUpdateWrapper.orderByDesc("execution_time");
                                autoExecutionRecordEntityUpdateWrapper.last("limit 1");

                                autoExecutionRecordEntityUpdateWrapper.set("execution_status", ExecutionStatusEnum.FAIL.getCode());
                                autoExecutionRecordService.update(autoExecutionRecordEntityUpdateWrapper);

                            }
                        }

                    });
                }
            }
        });

        // 结果数据，发送钉钉消息
        List<ApiFoxRunReportEntity> apiFoxRunReportEntities = this.queryByRunId(runId);
        this.sendDingDing(apiFoxRunReportEntities, attributionGroup);
    }


    /**
     * 解析，并获取 APIFOX 报告的 错误集合信息
     */
    public Map<String, Set<JSONObject>> getFailMetaInfoMap(JSONObject apifoxReportJson) {
        JSONArray failures = apifoxReportJson.getJSONObject("run").getJSONArray("failures");
        HashMap<String, Set<JSONObject>> map = new HashMap<>();

        failures.forEach(failItem -> {
            JSONObject failureObj = (JSONObject) failItem;
            JSONObject sourceObj = failureObj.getJSONObject("source");
            JSONObject errorObj = failureObj.getJSONObject("error");
            ApifoxRespMetaInfoDTO metaInfo = sourceObj.getObject("metaInfo", ApifoxRespMetaInfoDTO.class);
            final String stepId = metaInfo.getHttpApiId();

            Set<JSONObject> errorList = ObjectUtils.isNotEmpty(map.get(stepId)) ?
                    map.get(stepId) :
                    new HashSet<>();
            errorList.add(errorObj);
            if (ObjectUtils.isNotEmpty(stepId)) {
                map.put(stepId, errorList);
            }
        });
        return map;
    }


    /**
     * ApiFox 结果报告：钉钉通知
     */
    public void sendDingDing(List<ApiFoxRunReportEntity> entityList, AttributionGroupEnum attributionGroup) {
        // 结果数据，发送钉钉消息
        StringBuffer msg = new StringBuffer();
        msg.append("## ").append(attributionGroup).append("组-各成员自动化执行结果通知：  \n\n  ");

        // 小组数据统计
        final BigDecimal groupRunTotalSum = BigDecimal.valueOf(entityList.stream().map(ApiFoxRunReportEntity::getTotalRuns).reduce(0, Integer::sum));
        final BigDecimal groupRunSuccessSum = BigDecimal.valueOf(entityList.stream().map(ApiFoxRunReportEntity::getSuccessRuns).reduce(0, Integer::sum));
        final BigDecimal groupRunFailureSum = BigDecimal.valueOf(entityList.stream().map(ApiFoxRunReportEntity::getFailureRuns).reduce(0, Integer::sum));

        final BigDecimal groupSuccessRate = groupRunTotalSum.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                groupRunSuccessSum.divide(groupRunTotalSum, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        final BigDecimal groupFailureRate = groupRunTotalSum.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                groupRunFailureSum.divide(groupRunTotalSum, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));


        msg.append(" **[").append("小组").append(":](http://47.242.73.37:2080/app/application/apifox-68db96e1b752566623a315fa)**  \n\n  ")
                .append("-  **TotalCount:** ").append(groupRunTotalSum).append("  \n\n  ")
                .append("-  **Fail: <font color=red>").append(groupRunFailureSum).append("</font>**   \n\n  ")
                .append("-  Success: ").append(groupRunSuccessSum).append("  \n\n  ")
                .append("-  **FailRate: <font color=red>").append(groupFailureRate).append("% </font>**    \n\n  ")
                .append("-  **SuccessRate: <font color=green>").append(groupSuccessRate).append("% </font>** \n\n  ");


        // 组员数据统计
        entityList.forEach((obj) -> {


            final Double failureRate = obj.getFailureRate() > 0 ? obj.getFailureRate() * 100 : obj.getFailureRate();
            final Double successRate = obj.getSuccessRate() > 0 ? obj.getSuccessRate() * 100 : obj.getSuccessRate();


            msg.append(" **[").append(obj.getResponsiblePerson()).append(":](http://47.242.73.37:2080/app/application/apifox-68db9beeb752566623a31601?reportId=").append(obj.getId()).append(")**  \n\n  ")
                    .append("-  **TotalCount:** ").append(obj.getTotalRuns()).append("  \n\n  ")
                    .append("-  **Fail: <font color=red>").append(obj.getFailureRuns()).append("</font>**   \n\n  ")
                    .append("-  Success: ").append(obj.getSuccessRuns()).append("  \n\n  ")
                    .append("-  **FailRate: <font color=red>").append(failureRate).append("% </font>**    \n\n  ")
                    .append("-  **SuccessRate: <font color=green>").append(successRate).append("% </font>** \n\n  ")

            ;
        });
        msg.append("> **ApiFox_每日定时运行汇总报告:[点击查看](http://47.242.73.37:2080/app/application/apifox-68db96e1b752566623a315fa)**  \n  ")
        ;


        log.info("Apifox每日运行报告推送钉钉: 成功");
        log.info(msg.toString());

        // 推送钉钉群消息
        DingTalkUtils.sendMarkdownMessageOfApiFox("各成员自动化执行结果通知:", msg.toString());

    }


    /**
     * 报告文件读取逻辑，默认取最新的一条json文件
     */
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

        // 获取到最新的报告和当天日期不匹配则抛异常 和 群里通知
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String currentDay = sdf.format(new Date());
        if (!latestFile.getName().contains(currentDay)) {
            final String errorMessage = String.format("%s组执行失败，最新报告文件为： %s", containsStr, latestFile.getName());
            DingTalkUtils.sendMarkdownMessageOfApiFox("各成员自动化执行结果通知:", errorMessage);
            throw new RuntimeException("ApiFox 报告文件获取异常，最新文件为：".concat(latestFile.getName()));
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
