package com.miller.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.AutoCaseRoiChart;
import com.miller.entity.dto.PageAutoCaseRoiChartDTO;
import com.miller.mapper.AutoCaseRoiChartMapper;
import com.miller.service.AutoCaseRoiChartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * todo 待完善
     * @param pageAutoCaseRoiChartDTO
     * @return
     */
    @Override
    public Map<String, Object> getAutoCaseRoiChartList(PageAutoCaseRoiChartDTO pageAutoCaseRoiChartDTO) {

        int pageNo = pageAutoCaseRoiChartDTO.getPageNo();
        int pageSize = pageAutoCaseRoiChartDTO.getPageSize();
        Page<AutoCaseRoiChart> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AutoCaseRoiChart> queryWrapper = new QueryWrapper<>();

        Page<AutoCaseRoiChart> autoCaseRoiChartPage = autoCaseRoiChartMapper.selectPage(page, queryWrapper);
        List<AutoCaseRoiChart> records = autoCaseRoiChartPage.getRecords();
        long total = autoCaseRoiChartPage.getTotal();

        Map<String, Object> result = new HashMap<>();
        result.put("list",records);
        result.put("total",total);


        return result;
    }
}
