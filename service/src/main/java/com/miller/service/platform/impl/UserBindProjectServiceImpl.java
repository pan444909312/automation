package com.miller.service.platform.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.platform.UserBindProject;
import com.miller.mapper.platform.UserBindProjectMapper;
import com.miller.service.platform.UserBindProjectService;
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

}
