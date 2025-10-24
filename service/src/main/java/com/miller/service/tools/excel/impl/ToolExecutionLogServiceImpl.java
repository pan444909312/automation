package com.miller.service.tools.excel.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.tools.ToolExecutionLogEntity;
import com.miller.mapper.tool.ToolExecutionLogMapper;
import com.miller.service.tools.excel.ToolExecutionLogService;
import org.springframework.stereotype.Service;

@Service
public class ToolExecutionLogServiceImpl extends ServiceImpl<ToolExecutionLogMapper, ToolExecutionLogEntity>
        implements ToolExecutionLogService {

}
