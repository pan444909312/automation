package com.miller.demo.loginv4;

import com.miller.demo.dto.external.CalculatorEntity;
import com.miller.demo.loginv4.mapper.CalculatorMapper;
import com.miller.demo.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试数据
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/24 15:35:46
 */
@Disabled
@EnvTag.Test
@TestFramework
@DisplayName("计算器测试")
public class CalculatorTests {
    private static SqlSession sqlSession;
    private static CalculatorMapper calculatorMapper;

    @BeforeAll
    public static void beforeAll() throws IOException {
        sqlSession = DBUtils.getDBOfDemo();
        calculatorMapper = sqlSession.getMapper(CalculatorMapper.class);
    }

    @RepeatedTest(value = 2)
    @DisplayName("测试插入语句")
    void testInsert() {
        CalculatorEntity calculatorEntity = new CalculatorEntity();
        calculatorEntity.setFirstNumber(2.0);
        calculatorEntity.setSecondNumber(3.0);
        calculatorEntity.setResult(calculatorEntity.getFirstNumber() + calculatorEntity.getSecondNumber());
        int insert = calculatorMapper.insert(calculatorEntity);
        assertThat(insert).isGreaterThan(0);
        System.out.println("insert result: " + insert + " --> " + calculatorEntity.getId());
    }
}
