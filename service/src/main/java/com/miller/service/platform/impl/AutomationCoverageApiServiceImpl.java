package com.miller.service.platform.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.mapper.AutomationCoverageApiMapper;
import com.miller.service.platform.AutomationCoverageApiService;
import org.springframework.stereotype.Service;

@Service
public class AutomationCoverageApiServiceImpl extends ServiceImpl<AutomationCoverageApiMapper, AutomationCoverageApiEntity> implements AutomationCoverageApiService {
} 