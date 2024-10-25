package com.miller.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.AutoCaseRoi;
import com.miller.entity.AutoExecutionRecord;
import com.miller.entity.AutoExecutionRecordEntity;
import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.dto.PageAutoCaseExecutionRecordDTO;
import com.miller.entity.vo.AutoCaseExecutionRecordVO;
import com.miller.mapper.AutoExecutionRecordMapper;
import com.miller.service.AutoCaseRoiService;
import com.miller.service.AutoExecutionRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.util.TimestampUtils;
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
public class AutoExecutionRecordServiceImpl extends ServiceImpl<AutoExecutionRecordMapper, AutoExecutionRecord> implements AutoExecutionRecordService {

    @Autowired
    AutoExecutionRecordMapper autoExecutionRecordMapper;

    @Autowired
    AutoCaseRoiService autoCaseRoiService;

    /**
     * 已废弃
     * @param pageAutoCaseExecutionRecordDTO
     * @return
     */
    @Override
    public Map<String,Object> listAutoCase(PageAutoCaseExecutionRecordDTO pageAutoCaseExecutionRecordDTO){
        IPage<AutoExecutionRecord> autoExecutionRecordPage = new Page<>(pageAutoCaseExecutionRecordDTO.getPageNo(), pageAutoCaseExecutionRecordDTO.getPageSize());
        QueryWrapper<AutoExecutionRecord> queryWrapper = new QueryWrapper<>();

        String scenarioId = pageAutoCaseExecutionRecordDTO.getScenarioId();
        Date executionStartTime = pageAutoCaseExecutionRecordDTO.getExecutionStartTime();
        Date executionEndTime = pageAutoCaseExecutionRecordDTO.getExecutionEndTime();
        List<String> executionUserList = pageAutoCaseExecutionRecordDTO.getExecutionUserList();
        List<String> executionStatusList = pageAutoCaseExecutionRecordDTO.getExecutionStatusList();
        List<String> executionTypeList = pageAutoCaseExecutionRecordDTO.getExecutionTypeList();
        Integer orderBy = pageAutoCaseExecutionRecordDTO.getOrderBy();
        Integer developmentTimeSymbol = pageAutoCaseExecutionRecordDTO.getDevelopmentTimeSymbol();
        Integer maintenanceTimeSymbol = pageAutoCaseExecutionRecordDTO.getMaintenanceTimeSymbol();
        Integer manualTestTimeSymbol = pageAutoCaseExecutionRecordDTO.getManualTestTimeSymbol();

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
        // todo 各时间成本判断


        if (orderBy == 1) {
            queryWrapper.orderByDesc("execution_time");
        } else {
            queryWrapper.orderByAsc("execution_time");
        }


        IPage<AutoExecutionRecord> page = autoExecutionRecordMapper.selectPage(autoExecutionRecordPage, queryWrapper);
        List<AutoExecutionRecord> records = page.getRecords();
        long total = page.getTotal();

        // 数据组装
        AutoCaseExecutionRecordVO autoCaseExecutionRecordVO;
        ArrayList<AutoCaseExecutionRecordVO> autoCaseExecutionRecordVOS = new ArrayList<>();
        for (AutoExecutionRecord record : records) {
            autoCaseExecutionRecordVO = new AutoCaseExecutionRecordVO();
            BeanUtils.copyProperties(record,autoCaseExecutionRecordVO);
            autoCaseExecutionRecordVO.setExecutionStatusDesc(ExecutionStatusEnum.getValueByKey(Integer.parseInt(record.getExecutionStatus())));
            autoCaseExecutionRecordVO.setExecutionTypeDesc(ExecutionTypeEnum.getValueByKey(Integer.parseInt(record.getExecutionType())));
            autoCaseExecutionRecordVO.setExecutionTime(TimestampUtils.timestampToDateStr(record.getExecutionTime()));

            AutoCaseRoi autoCaseRoi = autoCaseRoiService.getOne(new QueryWrapper<AutoCaseRoi>().eq("scenario_id", record.getScenarioId()));
            autoCaseExecutionRecordVO.setScenarioName(autoCaseRoi.getScenarioName());
            autoCaseExecutionRecordVO.setMaintenanceTime(autoCaseRoi.getTimes());
            autoCaseExecutionRecordVO.setDevelopmentTime(autoCaseRoi.getDevelopmentTime());
            autoCaseExecutionRecordVO.setManualTestTime(autoCaseRoi.getManualTestTime());

            autoCaseExecutionRecordVOS.add(autoCaseExecutionRecordVO);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list",autoCaseExecutionRecordVOS);
        return result;
    }

    @Override
    public  Map<String,Object> listAutoCaseRecord(PageAutoCaseExecutionRecordDTO pageAutoCaseExecutionRecordDTO){
        Page<AutoExecutionRecord> autoExecutionRecordPage = new Page<>(pageAutoCaseExecutionRecordDTO.getPageNo(), pageAutoCaseExecutionRecordDTO.getPageSize());

        Page<AutoExecutionRecordEntity> autoExecutionRecordEntityPage = autoExecutionRecordMapper.selectPageByCondition(autoExecutionRecordPage,pageAutoCaseExecutionRecordDTO);
        List<AutoExecutionRecordEntity> records = autoExecutionRecordEntityPage.getRecords();
        long total = autoExecutionRecordEntityPage.getTotal();

        HashMap<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list",records);
        return result;
    }
}
