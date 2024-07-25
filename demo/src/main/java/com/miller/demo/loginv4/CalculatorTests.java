package com.miller.demo.loginv4;

import com.miller.demo.dto.external.CalculatorEntity;
import com.miller.demo.loginv4.mapper.CalculatorMapper;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.PropertiesUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * 测试数据
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/24 15:35:46
 */
@EnvTag.Test
@TestFramework
@DisplayName("计算器测试")
public class CalculatorTests {
    private static final String mySqlUrl = new PropertiesUtils().getProperty(CalculatorTests.class, "spring.datasource.url");
    private static final String userName = new PropertiesUtils().getProperty(CalculatorTests.class, "spring.datasource.username");
    private static final String passWord = new PropertiesUtils().getProperty(CalculatorTests.class, "spring.datasource.password");
    private static SqlSession sqlSession;
    private static CalculatorMapper calculatorMapper;

    @BeforeAll
    public static void beforeAll() throws IOException {
        MyBatisPlusConfig myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSession = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource());
        calculatorMapper = sqlSession.getMapper(CalculatorMapper.class);
    }

    @Test
    @DisplayName("测试插入语句")
    void testInsert() {
        CalculatorEntity calculatorEntity = new CalculatorEntity();
        calculatorEntity.setFirstNumber(1.0);
        calculatorEntity.setSecondNumber(2.0);
        calculatorEntity.setResult(calculatorEntity.getFirstNumber() + calculatorEntity.getSecondNumber());
        int insert = calculatorMapper.insert(calculatorEntity);
        System.out.println("insert result: " + insert);
    }
}
