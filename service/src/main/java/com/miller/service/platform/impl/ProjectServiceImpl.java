package com.miller.service.platform.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.platform.Project;
import com.miller.mapper.platform.ProjectMapper;
import com.miller.service.platform.ProjectService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

}
