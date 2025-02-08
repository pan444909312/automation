package com.miller.service.platform.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.platform.UserBindDept;
import com.miller.entity.platform.UserBindProject;
import com.miller.mapper.platform.UserBindDeptMapper;
import com.miller.mapper.platform.UserBindProjectMapper;
import com.miller.service.platform.UserBindProjectService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目下的用户 服务实现类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Service
public class UserBindProjectServiceImpl extends ServiceImpl<UserBindProjectMapper, UserBindProject> implements UserBindProjectService {

    @Autowired
    private UserBindProjectMapper userBindProjectMapper;

    @Override
    public UserBindProject selectByUserId(String userId) {
        return userBindProjectMapper.selectByUserId(userId);
    }

    @Override
    public void saveOrUpdate(String deptId, String userId) {

        // 校验 user 和 dept 是否有映射关系，没有则创建一个
        UserBindProject userBindProject = this.selectByUserId(userId);
        long currentTimeMillis = System.currentTimeMillis();
        if (ObjectUtils.isEmpty(userBindProject)) {
            userBindProject = new UserBindProject();
            userBindProject.setUserProjectId(ULIDUtils.generateULID());
            userBindProject.setCreateTime(currentTimeMillis);
        }
        userBindProject
                .setUserId(userId)
                .setProjectId(deptId)
                .setUpdateTime(currentTimeMillis);
        ;

        this.saveOrUpdate(userBindProject);
    }


}
