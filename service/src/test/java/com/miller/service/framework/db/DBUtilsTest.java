package com.miller.service.framework.db;

import com.miller.common.util.ULIDUtils;
import com.miller.service.framework.dto.IssuesDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 * 测试用例集
 *
 * @author Miller Shan
 * @see DBUtils
 */
@Disabled
public class DBUtilsTest {
    private static final String mySqlUrl = "jdbc:mysql://hp-polar-test-business-master-pub.mysql.polardb.rds.aliyuncs.com:3306:3306/ct_test";
    private static final String userName = "automation";
    private static final String passWord = "20AR@UJsobwLBdih";
    private static DBUtils dbUtils;

    @BeforeAll
    public static void beforeAll() {
        // 初始化，链接数据库
        dbUtils = new DBUtils(mySqlUrl, userName, passWord);
    }

    @DisplayName("Query multiple records from DB then return List")
    @Test
    public void testQueryMultiRecodes() {
        // Given
        String sql = "SELECT * FROM issues";
        // When
        List<IssuesDTO> issues = dbUtils.queryMultiObjectReturnList(sql, IssuesDTO.class);
        // Then
        // issues.forEach(System.out::println);
        assertThat(issues, notNullValue());
    }

    @DisplayName("Query one recode from DB then return Java Bean")
    @Test
    public void testQueryOneRecodeByObject() {
        // Given
        String sql = "SELECT * FROM issues where issue_id = ?";
        // When
        IssuesDTO issuesDTO = dbUtils.queryOneObjectReturnObject(sql, IssuesDTO.class, "asdf");
        // Then
        assertThat(issuesDTO, nullValue());
    }

    @DisplayName("Query one recode from DB then return Map ")
    @Test
    public void testQueryOneRecodeByMap() {
        // Given
        String sql = "SELECT * FROM issues where issue_id = ?";
        // When
        Map<String, Object> columnAndValueMap = dbUtils.queryOneObjectReturnMap(sql, "asdf");
        // Then
        assertThat(columnAndValueMap, nullValue());
    }

    @DisplayName("Query multiple records from DB, but sql has multiple values and then return List")
    @Test
    public void testQueryMultiRecodesByList() {
        // Given
        List<String> ids = new ArrayList<>();
        ids.add("3989b361-4e7d-4e0f-aba5-5986e6816f51");
        ids.add("76b83db6-2e31-4a9d-8ce7-b0c780a90608");
        ids.add("986515b0-f89b-41ac-88c2-be8e83b8cba7");

        String sql = "SELECT * FROM issues where issue_id in (" + dbUtils.transformListToString(ids) + ")";

        // When
        List<IssuesDTO> issues = dbUtils.queryMultiObjectReturnList(sql, IssuesDTO.class);
        // Then
        // issues.forEach(System.out::println);
        assertThat(issues, notNullValue());
    }

    @DisplayName("Test execute insert sql")
    @Test
    public void testExecuteInsertSql() {
        // Given
        String sql = "INSERT INTO `issues` (`issue_id`, `title`, `description`, `status`, `create_time`, `update_time`, `creator`, `update_user`, `project_id`, `handler`) " + "VALUES (?, 'aaa', 'aaa', '1', '1676609583092', '1676609583092', 'admin@aliyun.com', " + "'admin@aliyun.com', 'c0c42460-5945-464b-b8da-f869e979ca28', 'miller.shan@aliyun.com')";

        // When. sql 语句中？占位符的值
        Integer updateResult = dbUtils.executeInsertOrUpdateOrDelete(sql, ULIDUtils.generateULID());

        // Then
        assertThat(updateResult, Matchers.greaterThan(0));
    }

    @DisplayName("Test execute update sql")
    @Test
    public void testExecuteUpdateSql() {
        // Given
        String sql = "UPDATE `issues` SET `title` = 'test' WHERE (`issue_id` = ?)";

        // When
        Integer updateResult = dbUtils.executeInsertOrUpdateOrDelete(sql, ULIDUtils.generateULID());

        // Then
        assertThat(updateResult, Matchers.greaterThanOrEqualTo(0));
    }

    @DisplayName("Test execute delete sql")
    @Test
    public void testDeleteUpdateSql() {
        // Given
        String sql = "DELETE FROM `issues` WHERE (`issue_id` = ?)";
        // When
        Integer updateResult = dbUtils.executeInsertOrUpdateOrDelete(sql, ULIDUtils.generateULID());
        // Then
        assertThat(updateResult, Matchers.greaterThanOrEqualTo(0));
    }

    @DisplayName("Test invoke spring JdbcTemplate method")
    @Test
    public void testInvokeSpringJdbcTemplateMethod() {
        // Given
        String sql = "UPDATE `issues` SET `title` = 'test' WHERE (`issue_id` = 'test')";
        // When
        Integer updateResult = dbUtils.getJdbcTemplate().update(sql);
        // Then
        assertThat(updateResult, Matchers.greaterThanOrEqualTo(0));
    }

    @DisplayName("Test invoked Spring JdbcTemplate method but Method is Override")
    @Test
    public void testInvokedSpringJdbcTemplateMethodButMethodIsOverride() {
        // Given
        String sql = "UPDATE `issues` SET `title` = 'test' WHERE (`issue_id` = ?)";
        // When
        int[] ints = dbUtils.getJdbcTemplate().batchUpdate(sql, Stream.of(new Object[]{"111"}, new Object[]{"222"}).collect(Collectors.toList()));
        // Then
        assertThat(ints, notNullValue());
    }
}
