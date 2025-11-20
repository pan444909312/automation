package com.miller.controller.tools.apifox;

import com.miller.controller.tools.ResultVO;
import com.miller.entity.apifox.ApiFoxConfigEntity;
import com.miller.entity.apifox.ApiTestCaseCustomHttpRequestEntity;
import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.entity.util.Response;
import com.miller.pos.date.flow.WorkingTimeFlow;
import com.miller.service.apifox.ApiFoxConfigService;
import com.miller.service.apifox.ApiTestCaseCustomHttpRequestService;
import com.miller.service.apifox.ApifoxToolsService;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import com.miller.service.job.ApiFoxScheduled;
import com.miller.service.platform.AutomationCoverageApiService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/apifox/tools")
public class ApifoxToolsController {

    @Autowired
    private ApifoxToolsService apifoxToolsService;

    @Autowired
    private AutomationCoverageApiService automationCoverageApiService;

    @Autowired
    private  ApiFoxScheduled  apiFoxScheduled ;

    @Autowired
    private ApiFoxConfigService apiFoxConfigService;

    @Autowired
    private ApiTestCaseCustomHttpRequestService apiTestCaseCustomHttpRequestService;

    /**
     * Grafana 报表使用：查询时间范围内的工作日天数
     */
    @GetMapping("/getWorkingCount")
    public int getWorkingCount(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        log.info("开始时间：{},结束时间：{}", startTime, endTime);
        return WorkingTimeFlow.getDays(startTime, endTime);
    }






    /**
     * 调试接口：触发 apifox 报告，通知钉钉群
     */
    @GetMapping("/parsingReport")
    public boolean parsingReport(@RequestParam AttributionGroupEnum groupEnum){
        apifoxToolsService.parsingReport(groupEnum);
        return true;
    }

    /**
     * 调试接口：触发远程 apifox cli 执行接口，执行 ApiFox 用例集
     */
    @PostMapping("/execRemoteApifoxShell")
    public ResultVO execRemoteApifoxShell(@RequestParam("attributionGroupEnum") AttributionGroupEnum attributionGroupEnum){
//        AttributionGroupEnum groupEnum = AttributionGroupEnum.valueOf(groupCode);
        log.warn("开始执行 apifox cli 命令,{},{}",attributionGroupEnum.getT(),attributionGroupEnum);
        apiFoxScheduled.scheduledTaskAsync(attributionGroupEnum);
        return ResultVO.success("执行成功,请关注（自动化通知_Release_巡检）群通知 ~");
    }

    @PostMapping("/execRemoteApifoxShell/debug")
    public ResultVO execRemoteDebugApifoxShell(@RequestParam("taskId") String taskId){
        if (ObjectUtils.isEmpty(taskId) || taskId.isEmpty()){
            return ResultVO.failed("触发失败，taskId 为空");
        }

        apiFoxScheduled.scheduledTask(taskId);
        return ResultVO.success("触发成功，请关注群通知：自动化通知_Debug_调试");
    }


    @GetMapping("/getApifoxConfig")
    public Response<List<ApiFoxConfigEntity>> getApifoxConfig(){

        List<ApiFoxConfigEntity> list = apiFoxConfigService.list();


        return Response.success(list);
    }

    /**
     * 查询apifox 原生库： 测试步骤详细
     */
    @GetMapping("/apifox/step/info")
    public ResultVO<ApiTestCaseCustomHttpRequestEntity> queryById(@RequestParam String id){
        ApiTestCaseCustomHttpRequestEntity apiTestCaseCustomHttpRequestEntity = apiTestCaseCustomHttpRequestService.queryById(id);
        return ResultVO.success(apiTestCaseCustomHttpRequestEntity);
    }



    /**
     * 弃用：目前是手动执行全量覆盖，因为apifox后置无法获取到创建人信息，所以没法做归属
     * @param automationCoverageApiEntity
     * @return
     */
    @ApiOperation("Apifox 接口覆盖率接口")
    @PostMapping("/updateCoverage")
    public Boolean updateCoverage(@RequestBody AutomationCoverageApiEntity automationCoverageApiEntity) {
        return automationCoverageApiService.updateCoverageApi(automationCoverageApiEntity);
    }

    /**
     * 因为apifox 是局域网内应用，所以外部服务器无法调用，所以只能本地使用
     * @return
     */
    @ApiOperation("初始化： Apifox 接口覆盖率接口")
    @GetMapping("/initCoverage")
    public Set<String> initCoverage() throws Exception {
        return automationCoverageApiService.initApifoxCoverageApi();
    }
}
