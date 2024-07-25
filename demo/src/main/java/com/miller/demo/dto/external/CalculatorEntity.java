package com.miller.demo.dto.external;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 计算器表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/24 15:32:25
 */
@TableName("calculator")
@Data
public class CalculatorEntity {
    @TableId
    private Integer id;
    private Double firstNumber;
    private Double secondNumber;
    private Double result;
}
