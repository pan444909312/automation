package com.miller.userapp.util;

import com.miller.service.framework.db.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
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
public class PandaTestDBHelpful {
    /**
     * 数据库配置
     */
    protected static String mySqlUrl = "jdbc:mysql://hp-polar-test-business-master-pub.mysql.polardb.rds.aliyuncs.com:3306:3306/panda_test";
    protected static String userName = "panda_test";
    protected static String passWord = "Pan$te19*";
    protected static DBUtils dbUtils;

    static {
        dbUtils = new DBUtils(mySqlUrl, userName, passWord);
        log.info("DBUtils initialized successfully.");
    }

    /**
     * 执行 sql 语句，返回影响的记录行数。支持 单条sql、多条sql语句，多条sql语句时注意每条sql语句后的分号“；”不能少。
     * @param sql sql语句数组，每个元素可以包含多条用分号分隔的SQL语句
     * @return 影响的记录行
     */
    public static int[] executeInsertOrUpdateOrDelete(String... sql) {
        if (sql == null || sql.length == 0) {
            return new int[0];
        }

        List<String> processedSqlList = new ArrayList<>();
        for (String sqlStr : sql) {
            if (sqlStr != null && !sqlStr.trim().isEmpty()) {
                processedSqlList.addAll(splitSqlStatements(sqlStr));
            }
        }

        // 将处理后的SQL语句列表转换为数组
        String[] processedSqlArray = processedSqlList.toArray(new String[0]);
        return dbUtils.executeInsertOrUpdateOrDeleteByBatch(processedSqlArray);
    }

    /**
     * 将包含多条SQL语句的字符串按分号分割成单独的SQL语句列表
     * @param sqlStr 可能包含多条SQL语句的字符串
     * @return 分割后的SQL语句列表
     */
    private static List<String> splitSqlStatements(String sqlStr) {
        List<String> sqlList = new ArrayList<>();
        if (sqlStr == null || sqlStr.trim().isEmpty()) {
            return sqlList;
        }

        // 移除字符串前后的空白字符
        sqlStr = sqlStr.trim();

        // 如果字符串中不包含分号，直接添加
        if (!sqlStr.contains(";")) {
            sqlList.add(sqlStr);
            return sqlList;
        }

        // 按分号分割，并处理每个SQL语句
        String[] statements = sqlStr.split(";");
        for (String statement : statements) {
            statement = statement.trim();
            if (!statement.isEmpty()) {
                sqlList.add(statement);
            }
        }

        return sqlList;
    }

    /**
     * 查询一行记录，返回一个Map, 可通过 Map.get(column_name) 获取列名对应的列值
     *
     * @param sql  待执行的sql语句
     * @param args sql语句中的参数列表
     * @return 数据库中的列名、列值
     */
    public static Map<String, Object> executeSelectOneSql(String sql, @Nullable Object... args) {
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
    public static List<Map<String, Object>> executeSelectListSql(String sql, @Nullable Object... args) {
        return dbUtils.queryForListOfMaps(sql, args);
    }

}
