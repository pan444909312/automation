package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.entity.report.req.ApifoxAutoCaseRoiDto;
import com.miller.entity.report.req.PageAutoCaseExecutionRecordReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionRecordRespDTO;
import com.miller.mapper.report.AutoExecutionRecordMapper;
import com.miller.service.report.AutoCaseRoiService;
import com.miller.service.report.AutoExecutionRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.util.TimestampUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 自动化用例执行记录表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-10
 */
@Service
public class AutoExecutionRecordServiceImpl extends ServiceImpl<AutoExecutionRecordMapper, AutoExecutionRecordEntity> implements AutoExecutionRecordService {

    @Autowired
    AutoExecutionRecordMapper autoExecutionRecordMapper;

    @Autowired
    AutoCaseRoiService autoCaseRoiService;

    @Override
    public Map<String,Object> listAutoCase(PageAutoCaseExecutionRecordReqDTO pageAutoCaseExecutionRecordReqDTO){
        IPage<AutoExecutionRecordEntity> autoExecutionRecordPage = new Page<>(pageAutoCaseExecutionRecordReqDTO.getPageNo(), pageAutoCaseExecutionRecordReqDTO.getPageSize());
        QueryWrapper<AutoExecutionRecordEntity> queryWrapper = new QueryWrapper<>();

        String scenarioId = pageAutoCaseExecutionRecordReqDTO.getScenarioId();
        Date executionStartTime = pageAutoCaseExecutionRecordReqDTO.getExecutionStartTime();
        Date executionEndTime = pageAutoCaseExecutionRecordReqDTO.getExecutionEndTime();
        List<String> executionUserList = pageAutoCaseExecutionRecordReqDTO.getExecutionUserList();
        List<String> executionStatusList = pageAutoCaseExecutionRecordReqDTO.getExecutionStatusList();
        List<String> executionTypeList = pageAutoCaseExecutionRecordReqDTO.getExecutionTypeList();
        Integer orderBy = pageAutoCaseExecutionRecordReqDTO.getOrderBy();
        Integer developmentTimeSymbol = pageAutoCaseExecutionRecordReqDTO.getDevelopmentTimeSymbol();
        Integer maintenanceTimeSymbol = pageAutoCaseExecutionRecordReqDTO.getMaintenanceTimeSymbol();
        Integer manualTestTimeSymbol = pageAutoCaseExecutionRecordReqDTO.getManualTestTimeSymbol();
        Integer developmentTime = pageAutoCaseExecutionRecordReqDTO.getDevelopmentTime();
        Integer maintenanceTime = pageAutoCaseExecutionRecordReqDTO.getMaintenanceTime();
        Integer manualTestTime = pageAutoCaseExecutionRecordReqDTO.getManualTestTime();

        // 查询数据判断
        if (!StringUtils.isEmpty(scenarioId)){
            queryWrapper.eq("scenario_id",scenarioId);
        }
        if (executionStartTime != null) {
            queryWrapper.ge("execution_time", executionStartTime.getTime());
        }
        if (executionEndTime != null) {
            queryWrapper.le("execution_time", executionEndTime.getTime());
        }
        if (executionUserList != null && !executionUserList.isEmpty()){
            queryWrapper.in("execution_user",executionUserList);
        }
        if (executionStatusList != null && !executionStatusList.isEmpty()){
            queryWrapper.in("execution_status",executionStatusList);
        }
        if (executionTypeList != null && !executionTypeList.isEmpty()){
            queryWrapper.in("execution_type",executionTypeList);
        }

        if (developmentTimeSymbol == 1){
            queryWrapper.lt("development_time",developmentTime);
        }
        if (developmentTimeSymbol == 2){
            queryWrapper.eq("development_time",developmentTime);
        }
        if (developmentTimeSymbol == 3){
            queryWrapper.gt("development_time",developmentTime);
        }

        if (maintenanceTimeSymbol == 1){
            queryWrapper.lt("maintenance_time",maintenanceTime);
        }
        if (maintenanceTimeSymbol == 2){
            queryWrapper.eq("maintenance_time",maintenanceTime);
        }
        if (maintenanceTimeSymbol == 3){
            queryWrapper.gt("maintenance_time",maintenanceTime);
        }

        if (manualTestTimeSymbol == 1){
            queryWrapper.lt("manual_test_time",manualTestTime);
        }
        if (manualTestTimeSymbol == 2){
            queryWrapper.eq("manual_test_time",manualTestTime);
        }
        if (manualTestTimeSymbol == 3){
            queryWrapper.gt("manual_test_time",manualTestTime);
        }

        if (orderBy == 1) {
            queryWrapper.orderByDesc("execution_time");
        } else {
            queryWrapper.orderByAsc("execution_time");
        }


        IPage<AutoExecutionRecordEntity> page = autoExecutionRecordMapper.selectPage(autoExecutionRecordPage, queryWrapper);
        List<AutoExecutionRecordEntity> records = page.getRecords();
        long total = page.getTotal();

        // 数据组装
        AutoCaseExecutionRecordRespDTO autoCaseExecutionRecordRespDTO;
        ArrayList<AutoCaseExecutionRecordRespDTO> autoCaseExecutionRecordRespDTOS = new ArrayList<>();
        for (AutoExecutionRecordEntity record : records) {
            autoCaseExecutionRecordRespDTO = new AutoCaseExecutionRecordRespDTO();
            BeanUtils.copyProperties(record, autoCaseExecutionRecordRespDTO);
            autoCaseExecutionRecordRespDTO.setExecutionStatusDesc(ExecutionStatusEnum.getValueByKey(record.getExecutionStatus()));
            autoCaseExecutionRecordRespDTO.setExecutionTypeDesc(ExecutionTypeEnum.getValueByKey(record.getExecutionType()));
            autoCaseExecutionRecordRespDTO.setExecutionTime(TimestampUtils.timestampToDateStr(record.getExecutionTime()));

            autoCaseExecutionRecordRespDTO.setScenarioName(autoCaseRoiService.getAutoCaseNameByScenarioId(record.getScenarioId()));

            autoCaseExecutionRecordRespDTOS.add(autoCaseExecutionRecordRespDTO);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list", autoCaseExecutionRecordRespDTOS);
        return result;
    }


    @Override
    public  Map<String,Object> listAutoCaseRecord(PageAutoCaseExecutionRecordReqDTO pageAutoCaseExecutionRecordReqDTO){
        Page<AutoExecutionRecordEntity> autoExecutionRecordPage = new Page<>(pageAutoCaseExecutionRecordReqDTO.getPageNo(), pageAutoCaseExecutionRecordReqDTO.getPageSize());

        Page<AutoExecutionRecordEntity> autoExecutionRecordEntityPage = autoExecutionRecordMapper.selectPageByCondition(autoExecutionRecordPage, pageAutoCaseExecutionRecordReqDTO);
        List<AutoExecutionRecordEntity> records = autoExecutionRecordEntityPage.getRecords();
        long total = autoExecutionRecordEntityPage.getTotal();

        HashMap<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list",records);
        return result;
    }

    @Override
    public boolean apifoxSaveOrUpdate(AutoCaseRoiEntity autoCaseRoi, ApifoxAutoCaseRoiDto caseRoiDto) {
        AutoExecutionRecordEntity entity = new AutoExecutionRecordEntity();
        BeanUtils.copyProperties(autoCaseRoi,entity);

        // 设置执行状态
        entity.setExecutionType(caseRoiDto.getExecutionType());
        entity.setExecutionStatus(caseRoiDto.getExecutionStatus());


        //  当前时间为：执行时间
        long currentTimeMillis = System.currentTimeMillis();
        entity.setExecutionTime(currentTimeMillis);
        entity.setUpdateTime(currentTimeMillis);

        entity.setId(null);
        return this.saveOrUpdate(entity);
    }
}
