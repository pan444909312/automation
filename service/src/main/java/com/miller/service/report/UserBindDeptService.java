package com.miller.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.platform.UserBindDept;

public interface UserBindDeptService extends IService<UserBindDept> {

    UserBindDept selectByUserId(String userId);

    void saveOrUpdate(String deptId, String userId);
}
