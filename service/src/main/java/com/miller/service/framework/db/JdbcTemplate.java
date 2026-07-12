package com.miller.service.framework.db;

import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;
import java.util.List;

/**
 * 对 Spring 的 {@link org.springframework.jdbc.core.JdbcTemplate}进行封装，暂不做其它处理，主要是为了后期考虑对其做二次处理。
 * 比如: 目前虽然代码中调用的都是 Spring 的 {@link org.springframework.jdbc.core.JdbcTemplate} 类中的方法，但是后期如果想统一进行
 * 二次处理，那么只需要重新父类中的方法即可实现对原始方法的二次处理。对于框架中的代码是无感的。
 *
 * @author Miller Shan
 * @version 1.0.0
 */
public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {
    public JdbcTemplate() {
        super();
    }

    public JdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int[] batchUpdate(String sql, List<Object[]> batchArgs) throws DataAccessException {
        System.out.println("重写父类的方法，进行二次处理");
        return super.batchUpdate(sql, batchArgs);
    }
}
