package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.report.AutoCaseChartFutureDataEntity;
import com.miller.entity.report.AutoCaseRoiChartEntity;
import com.miller.entity.report.req.PageAutoCaseRoiChartReqDTO;
import com.miller.entity.report.resp.AutoCaseRoiChartRespDTO;
import com.miller.mapper.report.AutoCaseRoiChartMapper;
import com.miller.service.report.AutoCaseChartFutureDataService;
import com.miller.service.report.AutoCaseRoiChartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.service.util.TimestampUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * <p>
 * 测试场景总ROI表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@Service
public class AutoCaseRoiChartServiceImpl extends ServiceImpl<AutoCaseRoiChartMapper, AutoCaseRoiChartEntity> implements AutoCaseRoiChartService {

    @Autowired
    AutoCaseRoiChartMapper autoCaseRoiChartMapper;

    @Autowired
    AutoCaseChartFutureDataService autoCaseChartFutureDataService;

    /**
     * @param pageAutoCaseRoiChartReqDTO
     * @return
     */
    @Override
    public Map<String, Object> getAutoCaseRoiChartList(PageAutoCaseRoiChartReqDTO pageAutoCaseRoiChartReqDTO) {

        int pageNo = pageAutoCaseRoiChartReqDTO.getPageNo();
        int pageSize = pageAutoCaseRoiChartReqDTO.getPageSize();
        Page<AutoCaseRoiChartEntity> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AutoCaseRoiChartEntity> queryWrapper = new QueryWrapper<>();
        Date createStartTime = pageAutoCaseRoiChartReqDTO.getCreateStartTime();
        Date createEndTime = pageAutoCaseRoiChartReqDTO.getCreateEndTime();
        List<Integer> executionTypeList = pageAutoCaseRoiChartReqDTO.getExecutionTypeList();
        //查询条件处理
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime());
        }
        if (executionTypeList != null && !executionTypeList.isEmpty()) {
            queryWrapper.in("execution_type", executionTypeList);
        }
        queryWrapper.orderByDesc("create_time");


        Page<AutoCaseRoiChartEntity> autoCaseRoiChartPage = autoCaseRoiChartMapper.selectPage(page, queryWrapper);
        List<AutoCaseRoiChartEntity> records = autoCaseRoiChartPage.getRecords();
        long total = autoCaseRoiChartPage.getTotal();

        //数据组装
        LinkedList<AutoCaseRoiChartRespDTO> autoCaseRoiChartRespDTOList = new LinkedList<>();
        AutoCaseRoiChartRespDTO autoCaseRoiChartRespDTO;
        for (AutoCaseRoiChartEntity record : records) {
            autoCaseRoiChartRespDTO = new AutoCaseRoiChartRespDTO();
            BeanUtils.copyProperties(record, autoCaseRoiChartRespDTO);

            //节省人日 = 累计收益/60/8 , 四舍五入 保留3位小数
            BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(String.valueOf(record.getSaveTime())) / 60 / 8);
            bd = bd.setScale(3, RoundingMode.HALF_UP);
            Double roundedValue = bd.doubleValue();
            autoCaseRoiChartRespDTO.setSavePersonDay(roundedValue);

            autoCaseRoiChartRespDTO.setCreateTime(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            autoCaseRoiChartRespDTOList.add(autoCaseRoiChartRespDTO);
        }

        //未来日期数据处理
        QueryWrapper<AutoCaseChartFutureDataEntity> autoCaseChartFutureDataQueryWrapper = new QueryWrapper<>();
        autoCaseChartFutureDataQueryWrapper.eq("chart_type", 1);
        if (executionTypeList != null && !executionTypeList.isEmpty()) {
            autoCaseChartFutureDataQueryWrapper.in("execution_type", executionTypeList);
        }
        autoCaseChartFutureDataQueryWrapper.orderByDesc("future_time");
        if (executionTypeList != null && !executionTypeList.isEmpty()) {
            autoCaseChartFutureDataQueryWrapper.last("limit "+ executionTypeList.size());
        }

        List<AutoCaseChartFutureDataEntity> autoCaseChartFutureDataEntityList = autoCaseChartFutureDataService.list(autoCaseChartFutureDataQueryWrapper);
        AutoCaseRoiChartRespDTO futureVo = new AutoCaseRoiChartRespDTO();
        long sum = 0L;
        for (AutoCaseChartFutureDataEntity futureData : autoCaseChartFutureDataEntityList) {
            sum = sum + futureData.getExpectedSaveTime();
        }
        futureVo.setSaveTime(sum);
        futureVo.setCreateTime(TimestampUtils.timestampToDateStr(autoCaseChartFutureDataEntityList.get(0).getFutureTime()));
        autoCaseRoiChartRespDTOList.addFirst(futureVo);


        Map<String, Object> result = new HashMap<>();
        result.put("list", autoCaseRoiChartRespDTOList);
        result.put("total", total);
        return result;
    }
}
