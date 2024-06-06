package com.miller.demo.loginv3.mapper;

import com.miller.demo.dto.external.User;
import com.miller.demo.loginv2.request.LoginV2RequestDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 添加对数据库 user 表的操作
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/27 13:58:40
 */
@Mapper
public interface UserMapper {


    /**
     * 查询多条记录数。{@link Results @Results}注解用于声明返回结果中数据库的字段名称
     * 与Java Bean中的属性名对应关系。如果数据库中的属性名称和Java Bean对象中相同，
     * 则不需要通过{@link Result @Result}注解声明，反之亦然。
     *
     * @return 列表 {@literal List<Calculator}
     */
    @Select("select user_id, name, email, password, status, create_time, update_time from user;")
    List<LoginV2RequestDTO> getUserList();

    /**
     * 根据参数执行动态SQL语句，SQL语句在对应的xml文件中定义
     *
     * @param loginV2RequestDTO {@link LoginV2RequestDTO}
     * @return 记录集
     */
    List<LoginV2RequestDTO> selectByCondition(@Param("user") LoginV2RequestDTO loginV2RequestDTO);

    /**
     * 根据ID更新指定的记录
     *
     * @param user {@link User}
     * @return 影响的记录数
     */
    @Update("update user set status = #{status} where user_id = #{userId}")
    Integer update(User user);

}
