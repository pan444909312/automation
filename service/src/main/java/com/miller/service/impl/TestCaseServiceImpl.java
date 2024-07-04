package com.miller.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.TestCaseEntity;
import com.miller.mapper.TestCaseMapper;
import com.miller.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/3 11:23:37
 */
@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCaseEntity> implements TestCaseService {
    @Autowired
    private TestCaseMapper testCaseMapper;

    public void debugScenarios() {
        System.out.println("test..........");
    }


}
