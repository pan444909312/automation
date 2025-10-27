package com.miller.service.framework.db.mybatis;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 测试 MyBatisPlus 配置
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/12/12 15:21:52
 */
@Disabled
public class MyBatisPlusConfigTest {
    private static CalculatorMapper calculatorMapper;
    private static String mySqlUrl = "jdbc:mysql://hp-polar-test-business-master-pub.mysql.polardb.rds.aliyuncs.com:3306/panda_test?allowPublicKeyRetrieval=true";
    private static String userName = "automation";
    private static String passWord = "20AR@UJsobwLBdih";

    @BeforeAll
    static void beforeAll() {
        var myBatisPlusConfig = new MyBatisPlusConfig();
        SqlSession sqlSession = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), MyBatisPlusConfigTest.class);
        calculatorMapper = sqlSession.getMapper(CalculatorMapper.class);
    }

    @Test
    void testUpdate() {
        LambdaUpdateWrapper<Calculator> calculatorLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        calculatorLambdaUpdateWrapper.eq(Calculator::getId, 1);
        calculatorLambdaUpdateWrapper.set(Calculator::getResult, 5);
        // 使用 new Calculator() 空对象并在 LambdaUpdateWrapper 中设置具体值那么只有在 LambdaUpdateWrapper 中明确指定的字段会被更新。
        calculatorMapper.update(new Calculator(), calculatorLambdaUpdateWrapper);
    }

    @Test
    void testUpdateByEntity() {
        Calculator calculator = new Calculator(1, 3.0, 5.0, 8.0);

        LambdaUpdateWrapper<Calculator> calculatorLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        calculatorLambdaUpdateWrapper.eq(Calculator::getId, 1);
        // 使用 calculator 对象并在 LambdaUpdateWrapper 中设置具体值那么会先用对象，然后在用 LambdaUpdateWrapper 中设置的值覆盖掉。
        calculatorLambdaUpdateWrapper.set(Calculator::getResult, 5);
        calculatorLambdaUpdateWrapper.set(Calculator::getSecondNumber, 4);
        calculatorMapper.update(calculator, calculatorLambdaUpdateWrapper);
    }

}

interface CalculatorMapper extends BaseMapper<Calculator> {
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class Calculator {
    @TableId
    private Integer id;
    private Double firstNumber;
    private Double secondNumber;
    private Double result;

}
