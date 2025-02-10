package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.common.util.DateUtils;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.report.AutoCaseChartFutureDataEntity;
import com.miller.entity.report.AutoCaseRoiChartEntity;
import com.miller.entity.report.req.PageAutoCaseRoiChartReqDTO;
import com.miller.entity.report.resp.AutoCaseRoiChartRespDTO;
import com.miller.entity.util.BasePageResponse;
import com.miller.entity.util.Response;
import com.miller.mapper.report.AutoCaseRoiChartMapper;
import com.miller.service.report.AutoCaseChartFutureDataService;
import com.miller.service.report.AutoCaseRoiChartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.common.util.TimestampUtils;
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
     * 条件分页查询测试场景总ROI表
     *
     * @param pageAutoCaseRoiChartReqDTO 查询对象
     * @return查询对象
     */
    @Override
    public Map<String, Object> getAutoCaseRoiChartList(PageAutoCaseRoiChartReqDTO pageAutoCaseRoiChartReqDTO) {

        int pageNo = pageAutoCaseRoiChartReqDTO.getPageNo();
        // 分页的size，需要按执行策略的枚举类型乘上去，因为是按执行策略保存，不然会可能会差出来当天缺少某几个执行策略的数据
        // 4条数据为一组，最后返回为一条数据
        int pageSize = pageAutoCaseRoiChartReqDTO.getPageSize() * ExecutionTypeEnum.values().length;
        Page<AutoCaseRoiChartEntity> page = new Page<>(pageNo, pageSize);

        QueryWrapper<AutoCaseRoiChartEntity> queryWrapper = new QueryWrapper<>();
        Date createStartTime = DateUtils.strToDate(pageAutoCaseRoiChartReqDTO.getCreateStartTime(),"yyyy-MM-dd");
        Date createEndTime = DateUtils.strToDate(pageAutoCaseRoiChartReqDTO.getCreateStartTime(),"yyyy-MM-dd");
        List<Integer> executionTypeList = pageAutoCaseRoiChartReqDTO.getExecutionTypeList();
        if (executionTypeList == null){
            //如果为空则默认 查所有类型
            executionTypeList = new ArrayList<>();
            for (ExecutionTypeEnum item : ExecutionTypeEnum.values()) {
                executionTypeList.add(item.getCode());
            }
        }
        //查询条件处理
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime() + 1000 * 60 * 60 * 24);
        }
        if (createStartTime != null || createEndTime != null){
            queryWrapper.or().eq("chart_date","2099/01/01");
        }
        queryWrapper.orderByDesc("chart_date");

        Page<AutoCaseRoiChartEntity> autoCaseRoiChartPage = autoCaseRoiChartMapper.selectPage(page, queryWrapper);
        List<AutoCaseRoiChartEntity> records = autoCaseRoiChartPage.getRecords();

        // 总数需要除以执行策略的选择个数，不然算出来的总个数是所有日期的ROI乘执行策略个人的总数
        long total = autoCaseRoiChartPage.getTotal() / ExecutionTypeEnum.values().length;

        //数据组装
        LinkedList<AutoCaseRoiChartRespDTO> autoCaseRoiChartRespDTOList = new LinkedList<>();
        long totalMaintenanceTimeSum = 0;
        long totalDevelopmentTimeSum = 0;
        int totalTimesSum = 0;
        long saveTimeSum = 0;
        double roi = 0;
        String lastChartDate = records.get(0).getChartDate();
        //  目前的个数是所以执行策略的，需要根据4个为一组，然后通过要查的执行策略，累加对应值计算 最终放进respList
        for (AutoCaseRoiChartEntity record : records) {
            if (!lastChartDate.equals(record.getChartDate())) {

                autoCaseRoiChartRespDTOList.add(saveAutoCaseRoiChartRespDTO(saveTimeSum, totalDevelopmentTimeSum, totalMaintenanceTimeSum, totalTimesSum, roi, lastChartDate));

                // 初始化下一组数据
                lastChartDate = record.getChartDate();
                totalMaintenanceTimeSum = 0;
                totalDevelopmentTimeSum = 0;
                totalTimesSum = 0;
                saveTimeSum = 0;
                roi = 0;
            }

            // 按照筛选的执行策略，累加各数值
            if (executionTypeList.contains(record.getExecutionType())) {
                totalMaintenanceTimeSum = totalMaintenanceTimeSum + record.getTotalMaintenanceTime();
                totalDevelopmentTimeSum = totalDevelopmentTimeSum + record.getTotalDevelopmentTime();
                totalTimesSum = totalTimesSum + record.getTimes();
                saveTimeSum = saveTimeSum + record.getSaveTime();
            }
        }
        // 循环出来后 需要再保存最后的autoCaseRoiChartRespDTO，不然会丢了最后一条数据
        autoCaseRoiChartRespDTOList.add(saveAutoCaseRoiChartRespDTO(saveTimeSum, totalDevelopmentTimeSum, totalMaintenanceTimeSum, totalTimesSum, roi, lastChartDate));


        //未来日期数据处理
        QueryWrapper<AutoCaseChartFutureDataEntity> autoCaseChartFutureDataQueryWrapper = new QueryWrapper<>();
        autoCaseChartFutureDataQueryWrapper.eq("chart_type", 1);
        if (!executionTypeList.isEmpty()) {
            autoCaseChartFutureDataQueryWrapper.in("execution_type", executionTypeList);
        }
        // 不能用future_time，如果改了未来日期计算几个月的配置值，会有问题
//        autoCaseChartFutureDataQueryWrapper.orderByDesc("future_time");
        autoCaseChartFutureDataQueryWrapper.orderByDesc("create_time");
        if (!executionTypeList.isEmpty()) {
            autoCaseChartFutureDataQueryWrapper.last("limit " + executionTypeList.size());
        }

        List<AutoCaseChartFutureDataEntity> autoCaseChartFutureDataEntityList = autoCaseChartFutureDataService.list(autoCaseChartFutureDataQueryWrapper);
        AutoCaseRoiChartRespDTO futureVo = new AutoCaseRoiChartRespDTO();
        long sum = 0L;
        for (AutoCaseChartFutureDataEntity futureData : autoCaseChartFutureDataEntityList) {
            sum = sum + futureData.getExpectedSaveTime();
        }
        futureVo.setSaveTime(sum);
        futureVo.setCreateTime(TimestampUtils.timestampToDateStr(autoCaseChartFutureDataEntityList.get(0).getFutureTime()));
        if (pageNo == 1 && Objects.equals(autoCaseRoiChartRespDTOList.get(0).getCreateTime(), "2099/01/01")){
            autoCaseRoiChartRespDTOList.set(0,futureVo);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", autoCaseRoiChartRespDTOList);
        result.put("total", total);
        result.put("futureData",futureVo);
        return result;
    }

    @Override
    public long getTotalDevelopTime(int executionType) {
        if (getLatestData(executionType) != null)
            return getLatestData(executionType).getTotalDevelopmentTime();
        return 0;
    }

    @Override
    public long getTotalMaintenanceTime(int executionType) {
        if (getLatestData(executionType) != null)
            return getLatestData(executionType).getTotalMaintenanceTime();
        return 0;
    }

    @Override
    public int getTotalTimes(int executionType) {
        if (getLatestData(executionType) != null)
            return getLatestData(executionType).getTimes();
        return 0;
    }

    @Override
    public long getTotalSaveTime(int executionType) {
        if (getLatestData(executionType) != null)
            return getLatestData(executionType).getSaveTime();
        return 0;
    }

    /**
     * 检查自动化用例执行趋势表，今日是否同步过对应执行类型的数据
     *
     * @param executionType 执行策略
     * @return 是 返回true，否 返回 false
     */
    @Override
    public boolean checkTodayHasData(int executionType) {
        // 昨天0：00
        long yesterdayStart = TimestampUtils.timestampToYesterdayMidnight(System.currentTimeMillis());
        // 今日0：00
        long yesterdayEnd = yesterdayStart + 60 * 60 * 24 * 1000;

        List<AutoCaseRoiChartEntity> autoCaseRoiChartEntityList = autoCaseRoiChartMapper.selectList(new QueryWrapper<AutoCaseRoiChartEntity>()
                .ge("create_time", yesterdayEnd)
                .eq("execution_type", executionType));
        return !autoCaseRoiChartEntityList.isEmpty();
    }

    /**
     * 获取最新的一条测试场景总ROI数据对象
     *
     * @return
     */
    private AutoCaseRoiChartEntity getLatestData(int executionType) {
        QueryWrapper<AutoCaseRoiChartEntity> queryWrapper = new QueryWrapper<AutoCaseRoiChartEntity>()
                .eq("execution_type", executionType)
                .orderByDesc("create_time")
                .last("limit 1");
        return autoCaseRoiChartMapper.selectOne(queryWrapper);
    }

    /**
     * 构建并返回roi报表的返回dto
     *
     * @param saveTimeSum
     * @param totalDevelopmentTimeSum
     * @param totalMaintenanceTimeSum
     * @param totalTimesSum
     * @param roi
     * @param date
     * @return
     */
    private AutoCaseRoiChartRespDTO saveAutoCaseRoiChartRespDTO(
            long saveTimeSum,
            long totalDevelopmentTimeSum,
            long totalMaintenanceTimeSum,
            int totalTimesSum,
            double roi,
            String date) {
        AutoCaseRoiChartRespDTO autoCaseRoiChartRespDTO = new AutoCaseRoiChartRespDTO();

        //节省人日 = 累计收益/60/8 , 四舍五入 保留3位小数
        BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(String.valueOf(saveTimeSum)) / 60 / 8);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        Double roundedValue = bd.doubleValue();
        autoCaseRoiChartRespDTO.setSavePersonDay(roundedValue);

        autoCaseRoiChartRespDTO.setTotalDevelopmentTime(totalDevelopmentTimeSum);
        autoCaseRoiChartRespDTO.setTotalMaintenanceTime(totalMaintenanceTimeSum);
        autoCaseRoiChartRespDTO.setTimes(totalTimesSum);
        autoCaseRoiChartRespDTO.setSaveTime(saveTimeSum);
        if (totalMaintenanceTimeSum + totalDevelopmentTimeSum != 0) {
            roi = (double) saveTimeSum / (totalMaintenanceTimeSum + totalDevelopmentTimeSum);
        }
        autoCaseRoiChartRespDTO.setRoi(roi == 0 ? "0" : String.valueOf(roi));
        autoCaseRoiChartRespDTO.setCreateTime(date);
        return autoCaseRoiChartRespDTO;
    }
}
