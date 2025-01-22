package com.miller.service.report.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.report.req.ApifoxAutoCaseRoiDto;
import com.miller.mapper.platform.DeptMapper;
import com.miller.mapper.platform.UserBindDeptMapper;
import com.miller.mapper.platform.UserMapper;
import com.miller.mapper.report.AutoCaseRoiMapper;
import com.miller.service.report.ApifoxAutoCaseRoiService;
import com.miller.service.report.AutoExecutionRecordService;
import com.miller.service.report.UserBindDeptService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ApifoxAutoCaseRoiServiceImpl extends ServiceImpl<AutoCaseRoiMapper, AutoCaseRoiEntity> implements ApifoxAutoCaseRoiService {

    @Autowired
    AutoCaseRoiMapper autoCaseRoiMapper;

    @Autowired
    AutoExecutionRecordService autoExecutionRecordService;

    @Autowired
    DeptMapper deptMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private UserBindDeptService userBindDeptService;

    @Autowired
    private UserBindDeptMapper userBindDeptMapper;

    // B 侧 Apifox 使用
    public boolean apifoxSaveOrUpdate(ApifoxAutoCaseRoiDto dto) {

        AutoCaseRoiEntity autoCaseRoi = autoCaseRoiMapper.findByScenarioId(dto.getScenarioId());

        long currentTimeMillis = System.currentTimeMillis();

        if (!ObjectUtils.isEmpty(autoCaseRoi)) {
            autoCaseRoi.setDevelopmentTime(dto.getDevelopmentTime());
            autoCaseRoi.setManualTestTime(dto.getManualTestTime());

            // 维护成本累计 ： 已有的时间
            autoCaseRoi.setMaintenanceTime(dto.getMaintenanceTime());

            //执行次数：每次+1
            final int times = Math.addExact(autoCaseRoi.getTimes(), 1);
            autoCaseRoi.setTimes(times);

            autoCaseRoi.setUpdateTime(currentTimeMillis);

        } else {
            autoCaseRoi = new AutoCaseRoiEntity();
            BeanUtils.copyProperties(dto, autoCaseRoi);

            // 初始化执行次数
            Integer times = ObjectUtils.isNotEmpty(dto.getTimes()) || dto.getTimes() > 0 ? dto.getTimes() : 1;
            autoCaseRoi.setTimes(times);

            //  初始化创建时间:  时间格式不对则当前创建时间
            String createTime = dto.getCreateTime();
            if (ObjectUtils.isNotEmpty(createTime)) {
                try {
                    Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(createTime, new ParsePosition(0));
                    long time = parse.getTime();
                    autoCaseRoi.setCreateTime(time);
                } catch (Exception e) {
                    autoCaseRoi.setCreateTime(currentTimeMillis);
                }
            } else {
                autoCaseRoi.setCreateTime(currentTimeMillis);
            }

        }

        //  总节省时间 = 执行次数 * 手工测试成本
        final long saveTime = Math.multiplyExact(dto.getManualTestTime(), autoCaseRoi.getTimes());
        autoCaseRoi.setSaveTime(saveTime);

        // roi = (开发*维护)/总节省成本
        double roi = (double) autoCaseRoi.getSaveTime() / (Math.addExact(autoCaseRoi.getDevelopmentTime(), autoCaseRoi.getMaintenanceTime()));
        autoCaseRoi.setRoi(String.valueOf(roi));

        // 写入负责人邮箱

        autoCaseRoi.setAuthor(dto.getEmail());


        Integer priority = ObjectUtils.isNotEmpty(dto.getPriority()) ? dto.getPriority() : 0;
        autoCaseRoi.setPriority(priority);


        // 写入数据
        this.saveOrUpdate(autoCaseRoi);
        // 写入记录表
        this.autoExecutionRecordService.apifoxSaveOrUpdate(autoCaseRoi, dto);

        return true;
    }

}
