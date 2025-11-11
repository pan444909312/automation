package com.miller.service.apifox.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.apifox.ApiTestCaseCustomHttpRequestEntity;
import com.miller.mapper.apifox.ApiTestCaseCustomHttpRequestMapper;
import com.miller.service.apifox.ApiTestCaseCustomHttpRequestService;
import com.miller.service.util.ApifoxDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ApiTestCaseCustomHttpRequestServiceImpl
        implements ApiTestCaseCustomHttpRequestService {

    @Autowired
    private ApifoxDBUtils apifoxDBUtils;


    public ApiTestCaseCustomHttpRequestEntity queryById(String id) {
        QueryWrapper<ApiTestCaseCustomHttpRequestEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        ApiTestCaseCustomHttpRequestMapper mapper = apifoxDBUtils.getMapper(ApiTestCaseCustomHttpRequestMapper.class);
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public List<ApiTestCaseCustomHttpRequestEntity> queryByIdList(Set<String> ids) {
        QueryWrapper<ApiTestCaseCustomHttpRequestEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        ApiTestCaseCustomHttpRequestMapper mapper = apifoxDBUtils.getMapper(ApiTestCaseCustomHttpRequestMapper.class);
        return mapper.selectList(queryWrapper);
    }


}
