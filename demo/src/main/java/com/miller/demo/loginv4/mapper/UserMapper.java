package com.miller.demo.loginv4.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.demo.loginv2.request.LoginV2RequestDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 添加对数据库 user 表的操作
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/27 13:58:40
 */
public interface UserMapper extends BaseMapper<LoginV2RequestDTO> {

    /**
     * 根据参数执行动态SQL语句，SQL语句在对应的xml文件中定义
     *
     * @param loginV2RequestDTO {@link LoginV2RequestDTO}
     * @return 记录集
     */
    List<LoginV2RequestDTO> selectByCondition(@Param("user") LoginV2RequestDTO loginV2RequestDTO);
}
