package com.miller.service.report.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.platform.UserBindDept;
import com.miller.mapper.platform.UserBindDeptMapper;
import com.miller.service.report.UserBindDeptService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBindDeptServiceImpl  extends ServiceImpl<UserBindDeptMapper, UserBindDept> implements UserBindDeptService {

    @Autowired
    private UserBindDeptMapper userBindDeptMapper;

    @Override
    public UserBindDept selectByUserId(String userId) {
        return userBindDeptMapper.selectByUserId(userId);
    }

    @Override
    public void saveOrUpdate(String deptId, String userId) {
        // 校验 user 和 dept 是否有映射关系，没有则创建一个
        UserBindDept userBindDept = this.selectByUserId(userId);
        long currentTimeMillis = System.currentTimeMillis();
        if (ObjectUtils.isEmpty(userBindDept)) {
            userBindDept = new UserBindDept();
            userBindDept.setUserDeptId(ULIDUtils.generateULID());
            userBindDept.setCreateTime(currentTimeMillis);
        }
        userBindDept
                .setUserId(userId)
                .setDeptId(deptId)
                .setUpdateTime(currentTimeMillis);
        ;

        this.saveOrUpdate(userBindDept);
    }
}
