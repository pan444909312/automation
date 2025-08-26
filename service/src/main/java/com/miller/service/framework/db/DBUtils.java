package com.miller.service.framework.db;

import com.miller.service.framework.util.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作工具，使用方法参考单元测试.
 *
 * @author Miller Shan
 * @version 1.0.0
 * @since 2023/10/25 20:03:44
 */
@Slf4j
public class DBUtils {
    private DataSource dataSource;
    /**
     * 对 Spring 的 {@link org.springframework.jdbc.core.JdbcTemplate}进行继承，保留了原始功能,并对外开放。
     */
    private JdbcTemplate jdbcTemplate;

    public DBUtils() {
    }

    /**
     * @see #getDataSourceByDruid(String, String, String)
     */
    public DBUtils(String mySqlUrl, String userName, String passWord) {
        getDataSourceByDruid(mySqlUrl, userName, passWord);
    }



    /**
     * @see JdbcUtilsByDruid#getDataSource(String, String, String)
     */
    private DataSource getDataSourceByDruid(String mySqlUrl, String userName, String passWord) {
        // 使用 Druid 实现
        JdbcUtilsByDruid jdbcUtilsByDruid = new JdbcUtilsByDruid();
        dataSource = jdbcUtilsByDruid.getDataSource(mySqlUrl, userName, passWord);
        jdbcTemplate = new JdbcTemplate(dataSource);

        return dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 执行 sql 语句，返回影响的记录行数
     *
     * @param sql  sql语句
     * @param args sql语句中使用占位符?替代的参数
     * @return 返回影响的记录行
     */
    public Integer executeInsertOrUpdateOrDelete(String sql, Object... args) {
        int update = jdbcTemplate.update(sql, args);
        return update;
    }

    /**
     * 执行批量插入、更新、删除
     * @param sql sql语句
     * @return 影响的记录行
     */
    public int[] executeInsertOrUpdateOrDeleteByBatch(final String... sql) {
        int[] ints = jdbcTemplate.batchUpdate(sql);
        return ints;
    }

    /**
     * 查询一条记录，返回一个Java Bean对象
     *
     * @param sql        待执行的sql语句
     * @param returnType 入参的类型，单独的“T”表示限制传入的参数类型
     * @param args       sql语句中的参数列表
     * @param <T>        占位符 <T> T 表示“返回值”是一个泛型，传入什么类型，就返回什么类型。<T>表示是泛型。
     * @return 表示返回的是T类型的数据, 如果无数据则返回 null
     * @throws DataAccessException
     */
    public <T> T queryOneObjectReturnObject(String sql, Class<T> returnType, @Nullable Object... args) throws DataAccessException {
        T t = null;
        try {
            t = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(returnType), args);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            //emptyResultDataAccessException.printStackTrace();
            log.warn("查询结果小于1. {}", emptyResultDataAccessException.getMessage());
        }
        return t;
    }

    /**
     * 查询多条记录，返回列表
     *
     * @param sql        待执行的sql语句
     * @param returnType 入参的类型，单独的“T”表示限制传入的参数类型
     * @param args       sql语句中的参数列表
     * @param <T>        占位符 <T> T 表示“返回值”是一个泛型，传入什么类型，就返回什么类型。<T>表示是泛型。
     * @return 表示返回的是T类型的数据
     * @throws DataAccessException
     */
    public <T> List<T> queryMultiObjectReturnList(String sql, Class<T> returnType, @Nullable Object... args) throws DataAccessException {
        List<T> queryForList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(returnType), args);
        return queryForList;
    }

    /**
     * 查询一条记录，返回一个Map
     *
     * @param sql  待执行的sql语句
     * @param args sql语句中的参数列表
     * @return 数据库中的列名、列值
     */
    public Map<String, Object> queryOneObjectReturnMap(String sql, @Nullable Object... args) {
        Map<String, Object> columnAndValueMap = null;
        try {
            columnAndValueMap = jdbcTemplate.queryForMap(sql, args);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            //emptyResultDataAccessException.printStackTrace();
            log.warn("查询结果小于1. {}", emptyResultDataAccessException.getMessage());
        }
        return columnAndValueMap;
    }
    /**
     * 执行 SELECT 查询语句，返回多条记录，每条记录用 Map 表示（列名 -> 列值）
     *
     * @param sql  要执行的 SQL 查询语句
     * @param args SQL 中的参数，可选
     * @return 查询结果的 List<Map<String, Object>>，每条记录对应一个 Map
     */
    public List<Map<String, Object>> queryForListOfMaps(String sql, @Nullable Object... args) {
        try {
            return jdbcTemplate.queryForList(sql, args);
        } catch (DataAccessException e) {
            log.error("数据库查询失败: {}", e.getMessage());
            throw e;
        }
    }


    /**
     * 转换 List 为 String，用于SQL语句中包含多个参数。不保证SQL注入问题。
     * 比如: ... where id in (1, 2, "3", "4")
     *
     * @param ids
     * @return 逗号分隔的字符串，如:'1', '2', 'a,', 'b'
     */
    public String transformListToString(List<String> ids) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String element : ids) {
            stringBuilder.append("'");
            stringBuilder.append(element);
            stringBuilder.append("',");
        }
        String result = stringBuilder.substring(0, stringBuilder.length() - 1);
        return result;
    }


    /**
     * apifox 数据源
     */
    public  DBUtils initApifoxDB(){
        return new DBUtils(new PropertiesUtils().getProperty(this.getClass(), "datasource.url.apifox"),
                new PropertiesUtils().getProperty(this.getClass(), "datasource.username.apifox"),
                new PropertiesUtils().getProperty(this.getClass(), "datasource.password.apifox"));
    }
}
