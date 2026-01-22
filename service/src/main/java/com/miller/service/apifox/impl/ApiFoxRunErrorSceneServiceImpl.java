package com.miller.service.apifox.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.apifox.ApiFoxRunErrorSceneEntity;
import com.miller.mapper.apifox.ApiFoxRunErrorSceneMapper;
import com.miller.service.apifox.ApiFoxRunErrorSceneService;
import org.springframework.stereotype.Service;

@Service
public class ApiFoxRunErrorSceneServiceImpl extends ServiceImpl<ApiFoxRunErrorSceneMapper, ApiFoxRunErrorSceneEntity> implements ApiFoxRunErrorSceneService {


    @Override
    public ApiFoxRunErrorSceneEntity findByCaseId(Long apiFoxCaseId) {
        QueryWrapper<ApiFoxRunErrorSceneEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("apifox_case_id", apiFoxCaseId)
                .last("order by id desc limit 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean updateToDel(Long id) {
        return this.baseMapper.updateToDel(id);
    }


}
