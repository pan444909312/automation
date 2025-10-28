package com.miller.delivery.testcase.工具;

import com.miller.service.framework.db.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * 操作 panda_test 库
 *
 * @author Miller Shan
 * @version 1.0.0
 * @since 2025/05/27 23:03:44
 */
@Slf4j
public class 数据库助手 {
    /**
     * 数据库配置
     */
    protected static String mySqlUrl = "jdbc:mysql://hp-polar-test-business-master.mysql.polardb.rds.aliyuncs.com:3306/panda_test";
    protected static String userName = "panda_test";
    protected static String passWord = "Pan$te19*";
    protected static DBUtils dbUtils;

    static {
        dbUtils = new DBUtils(mySqlUrl, userName, passWord);
        log.info("DBUtils initialized successfully.");
    }

    /**
     * 执行 sql 语句，返回影响的记录行数
     *
     * @param sql  sql语句
     * @param args sql语句中使用占位符?替代的参数
     * @return 返回影响的记录行
     */
    public static Integer 执行InsertOrUpdateOrDelete(String sql, Object... args) {
        Integer result = dbUtils.executeInsertOrUpdateOrDelete(sql, args);
        return result;
    }


    /**
     * 查询一行记录，返回一个Map, 可通过 Map.get(column_name) 获取列名对应的列值
     *
     * @param sql  待执行的sql语句
     * @param args sql语句中的参数列表
     * @return 数据库中的列名、列值
     */
    public static Map<String, Object> 执行Select语句返回一行记录(String sql, @Nullable Object... args) {
        Map<String, Object> columnAndValueMap = dbUtils.queryOneObjectReturnMap(sql, args);
        return columnAndValueMap;
    }

    /**
     * 查询多行记录,执行 SELECT 查询语句，返回多条记录，每条记录用 Map 表示（列名 -> 列值）
     *
     * @param sql  要执行的 SQL 查询语句
     * @param args SQL 中的参数，可选
     * @return 查询结果的 List<Map<String, Object>>，每条记录对应一个 Map
     */
    public static List<Map<String, Object>> 执行Select语句返回多行记录(String sql, @Nullable Object... args) {
        return dbUtils.queryForListOfMaps(sql, args);
    }

}
