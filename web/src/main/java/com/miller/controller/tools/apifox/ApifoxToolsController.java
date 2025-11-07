package com.miller.controller.tools.apifox;

import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.entity.util.Response;
import com.miller.pos.date.flow.WorkingTimeFlow;
import com.miller.service.apifox.ApiFoxConfigService;
import com.miller.service.apifox.ApifoxToolsService;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import com.miller.service.job.ApiFoxScheduled;
import com.miller.service.platform.AutomationCoverageApiService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/getWorkingCount")
    public int getWorkingCount(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        log.info("开始时间：{},结束时间：{}", startTime, endTime);
        return WorkingTimeFlow.getDays(startTime, endTime);
    }

    @PostMapping("/pushDingDing")
    public void pushDingDing(@RequestParam String access_token,
                             @RequestParam String timestamp,
                             @RequestParam String sign,
                             @RequestBody String body) {
        apifoxToolsService.sendDingDing(access_token, timestamp, sign, body);
    }


    /**
     * 触发 apifox 报告，通知钉钉群
     * @return
     */
    @GetMapping("/parsingReport")
    public boolean parsingReport(@RequestParam AttributionGroupEnum groupEnum){
        apifoxToolsService.parsingReport(groupEnum);
        return true;
    }

    /**
     * 触发 apifox shell，执行APIFOX用例集
     * @return
     */
    @GetMapping("/execRemoteApifoxShell")
    public boolean execRemoteApifoxShell(@RequestParam AttributionGroupEnum  attributionGroupEnum){
        apiFoxScheduled.scheduledTask(attributionGroupEnum);
        return true;
    }

    @GetMapping("/getApifoxConfig")
    public Response<String> getApifoxConfig(@RequestParam AttributionGroupEnum  attributionGroupEnum){

        // todo

        return Response.success(null);
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
