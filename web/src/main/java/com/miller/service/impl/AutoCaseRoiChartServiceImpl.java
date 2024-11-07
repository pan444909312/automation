package com.miller.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.AutoCaseChartFutureData;
import com.miller.entity.AutoCaseRoiChart;
import com.miller.entity.dto.PageAutoCaseRoiChartDTO;
import com.miller.entity.vo.AutoCaseRoiChartVo;
import com.miller.mapper.AutoCaseRoiChartMapper;
import com.miller.service.AutoCaseChartFutureDataService;
import com.miller.service.AutoCaseRoiChartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.util.TimestampUtils;
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
public class AutoCaseRoiChartServiceImpl extends ServiceImpl<AutoCaseRoiChartMapper, AutoCaseRoiChart> implements AutoCaseRoiChartService {

    @Autowired
    AutoCaseRoiChartMapper autoCaseRoiChartMapper;

    @Autowired
    AutoCaseChartFutureDataService autoCaseChartFutureDataService;

    /**
     * @param pageAutoCaseRoiChartDTO
     * @return
     */
    @Override
    public Map<String, Object> getAutoCaseRoiChartList(PageAutoCaseRoiChartDTO pageAutoCaseRoiChartDTO) {

        int pageNo = pageAutoCaseRoiChartDTO.getPageNo();
        int pageSize = pageAutoCaseRoiChartDTO.getPageSize();
        Page<AutoCaseRoiChart> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AutoCaseRoiChart> queryWrapper = new QueryWrapper<>();
        Date createStartTime = pageAutoCaseRoiChartDTO.getCreateStartTime();
        Date createEndTime = pageAutoCaseRoiChartDTO.getCreateEndTime();
        List<Integer> executionTypeList = pageAutoCaseRoiChartDTO.getExecutionTypeList();
        //查询条件处理
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime());
        }
        if (executionTypeList != null && !executionTypeList.isEmpty()){
            queryWrapper.in("execution_type",executionTypeList);
        }
        queryWrapper.orderByDesc("create_time");


        Page<AutoCaseRoiChart> autoCaseRoiChartPage = autoCaseRoiChartMapper.selectPage(page, queryWrapper);
        List<AutoCaseRoiChart> records = autoCaseRoiChartPage.getRecords();
        long total = autoCaseRoiChartPage.getTotal();

        //数据组装
        LinkedList<AutoCaseRoiChartVo> autoCaseRoiChartVoList = new LinkedList<>();
        AutoCaseRoiChartVo autoCaseRoiChartVo;
        for (AutoCaseRoiChart record : records) {
            autoCaseRoiChartVo = new AutoCaseRoiChartVo();
            BeanUtils.copyProperties(record, autoCaseRoiChartVo);

            //节省人日 = 累计收益/60/8 , 四舍五入 保留3位小数
            BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(String.valueOf(record.getSaveTime())) / 60 / 8);
            bd = bd.setScale(3, RoundingMode.HALF_UP);
            Double roundedValue = bd.doubleValue();
            autoCaseRoiChartVo.setSavePersonDay(roundedValue);

            autoCaseRoiChartVo.setCreateTime(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            autoCaseRoiChartVoList.add(autoCaseRoiChartVo);
        }

        //未来日期数据处理
        AutoCaseChartFutureData futureData = autoCaseChartFutureDataService.getOne(new QueryWrapper<AutoCaseChartFutureData>()
                .eq("chart_type", 1)
                .orderByDesc("future_time")
                .last("limit 1"));
        AutoCaseRoiChartVo futureVo = new AutoCaseRoiChartVo();
        futureVo.setSaveTime(futureData.getExpectedSaveTime());
        futureVo.setCreateTime(TimestampUtils.timestampToDateStr(futureData.getFutureTime()));
        autoCaseRoiChartVoList.addFirst(futureVo);


        Map<String, Object> result = new HashMap<>();
        result.put("list", autoCaseRoiChartVoList);
        result.put("total", total);
        return result;
    }
}
