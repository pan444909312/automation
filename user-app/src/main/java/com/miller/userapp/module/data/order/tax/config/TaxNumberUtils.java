package com.miller.userapp.module.data.order.tax.config;


import com.hungrypanda.app.server.common.consants.CommonConstants;
import com.hungrypanda.app.server.vo.gfo.GfoMoneySplitVO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class TaxNumberUtils {
    public static final Pattern NUMBER_PATTERN = Pattern.compile(".*[0-9].*");
    
    public static final BigDecimal HUNDRED = new BigDecimal("100");

    /**
     * BigDecimal加法
     * @author zhuyj
     * @date 2020年12月8日下午4:17:36
     */
    public static BigDecimal add(BigDecimal ... bd){
        BigDecimal result = BigDecimal.ZERO;
        if(null == bd){
            return result;
        }
        for (BigDecimal num : bd) {
            if(null != num){
                result = result.add(num);
            }
        }
        return result;
    }

    /**
     * BigDecimal加法
     * @author zhuyj
     * @date 2020年12月8日下午4:17:36
     */
    public static BigDecimal addObj(Object ... bd){
        BigDecimal result = BigDecimal.ZERO;
        if(null == bd){
            return result;
        }
        for (Object num : bd) {
            if(null != num){
                try {
                    result = result.add(new BigDecimal(num.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Integer加法
     * @author zhuyj
     * @date 2021年9月10日下午2:11:34
     */
    public static int add(Integer ... nums){
        int rslt = 0;
        if(null == nums){
            return rslt;
        }
        for (Integer num : nums) {
            if(null != num){
                rslt = rslt + num;
            }
        }
        return rslt;
    }

    /**
     * BigDecimal减法
     * @author zhuyj
     * @date 2020年12月8日下午4:17:30
     */
    public static BigDecimal subtract(BigDecimal ... bd){
        BigDecimal result = BigDecimal.ZERO;
        if(null == bd){
            return result;
        }
        for (int i = 0; i < bd.length; i++) {
            if(null == bd[i]){
                continue;
            }
            if(i == 0){
                result = bd[i];
                continue;
            }
            result = result.subtract(bd[i]);
        }
        return result;
    }

    /**
     * BigDecimal减法
     * @author zhuyj
     * @date 2020年12月8日下午4:17:30
     */
    public static String subtract(Object ... bd){
        BigDecimal result = BigDecimal.ZERO;
        if(null == bd){
            return result.toPlainString();
        }
        for (int i = 0; i < bd.length; i++) {
            if(null == bd[i]){
                continue;
            }
            if(i == 0){
                result = new BigDecimal (bd[i].toString());
                continue;
            }
            result = result.subtract(new BigDecimal (bd[i].toString()));
        }
        return result.toPlainString();
    }

    public static BigDecimal multiply(int scale, Object ... bd){
        BigDecimal rslt = BigDecimal.ZERO;
        BigDecimal zero = BigDecimal.ZERO.setScale(scale);
        if(null == bd){
            return zero;
        }
        try {
            for (int i = 0; i < bd.length; i++) {
                if(null == bd[i] || org.apache.commons.lang.StringUtils.isBlank(bd[i].toString())){
                    return zero;
                }
                BigDecimal num = new BigDecimal(bd[i].toString());
                if(BigDecimal.ZERO.compareTo(num) == 0){
                    return zero;
                }
                if(i == 0){
                    rslt = num;
                    continue;
                }
                rslt = rslt.multiply(num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rslt.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 除法
     * @param scale 保留小数位数
     * @author zhuyj
     * @date 2020年12月11日下午6:11:06
     */
    public static BigDecimal divideBd(int scale, Object ... bd){
        BigDecimal rslt = BigDecimal.ZERO;
        BigDecimal zero = BigDecimal.ZERO;
        if(null == bd){
            return zero;
        }
        try {
            for (int i = 0; i < bd.length; i++) {
                if(null == bd[i] || org.apache.commons.lang.StringUtils.isBlank(bd[i].toString())){
                    return zero;
                }
                BigDecimal num = new BigDecimal(bd[i].toString());
                if(BigDecimal.ZERO.compareTo(num) == 0){
                    return zero;
                }
                if(i == 0){
                    rslt = num;
                    continue;
                }
                rslt = rslt.divide(num, 9, RoundingMode.HALF_UP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rslt.setScale(scale, RoundingMode.HALF_UP);
    }

    public static Double roundScale(Double amount,int scale) {
        if (amount == null) {
            return null;
        }
        if(scale < 0){
            scale = 2;
        }
        BigDecimal b = new BigDecimal(amount);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static String NumberFormat(double d, String format) throws NumberFormatException {
        if (StringUtils.isEmpty(format)) {
            throw new NumberFormatException("formatUtil.NumberFormat error!!!");
        } else {
            DecimalFormat df = new DecimalFormat(format);
            return df.format(d);
        }
    }

    public static String percentToFive(Integer percent){
        return String.valueOf(
                new BigDecimal(Optional.ofNullable(percent).orElse(0))
                        .divide(new BigDecimal(20))
                        .setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
    }

    /**
     * 处理评分
     * @param evaluation
     * @param version
     */
    public static String dealEvaluation(String evaluation, String version){
        //没有版本号返回百分值
        if (StringUtils.isBlank(version) && CommonConstants.STRING_ZERO.equals(evaluation)){
            return CommonConstants.STRING_HUNDRED;
        }
        if (StringUtils.isBlank(version)){
            return evaluation;
        }
        if ((StringUtils.isBlank(evaluation) || CommonConstants.STRING_ZERO.equals(evaluation))){
            return CommonConstants.STRING_FIVE;
        }
        if (StringUtils.isNotBlank(version)){
            return percentToFive(Integer.valueOf(evaluation));
        }
        return evaluation;
    }

    /**
     * 保留两位小数
     *
     * @return
     */
    public static String divide(Integer num, Integer divisor) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format((float) num / divisor);
    }

    /**
     * 乘100
     *
     * @return
     */
    public static Integer multiplyHundredInt(String num) {
        return Integer.valueOf(multiply(num, "100"));
    }
    
    /**
     * 乘100
     *
     * @return
     */
    public static Integer x100Int(BigDecimal num) {
    	if(null == num) return 0;
        return num.multiply(HUNDRED).intValue();
    }

    /**
     * @return
     */
    public static String multiply(String num, String multiplyNum) {
        BigDecimal multiplier = new BigDecimal(num);
        BigDecimal result = multiplier.multiply(new BigDecimal(multiplyNum));
        return result.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 处理gfo订单价格，返回的价格单位为分
     */
    public static Integer convertGfoPrice(String units, Integer nanos) {
        String gfoCheckPrice;
        if (StringUtils.isBlank(units)) {
            if (Objects.isNull(nanos)){
                return 0;
            }
            gfoCheckPrice = divide(nanos, 1000000000);
        } else if (Objects.isNull(nanos)) {
            gfoCheckPrice = units;
        } else {
            BigDecimal nanosNum = BigDecimal.valueOf(nanos).divide(BigDecimal.valueOf(1000000000), 2, RoundingMode.HALF_UP);
            gfoCheckPrice = new BigDecimal(units).add(nanosNum).toString();
        }
        return multiplyHundredInt(gfoCheckPrice);
    }

    /**
     * 谷歌下单拆分金额，参数单位为分，金额必须是分为单位,返回整数部分和小数点后两位部分
     */
    public static GfoMoneySplitVO splitGfoMoney(Integer price) {
        String divideNum = divide(price, 100);
        GfoMoneySplitVO gfoMoneySplitVO = new GfoMoneySplitVO();
        gfoMoneySplitVO.setUnits(divideNum.split("\\.")[0]);
        gfoMoneySplitVO.setNanos(Integer.valueOf(multiply("0." + divideNum.split("\\.")[1], "1000000000")));
        return gfoMoneySplitVO;
    }

    public static void main(String[] args) {
        GfoMoneySplitVO gfoMoneySplitVO = TaxNumberUtils.splitGfoMoney(150);
        System.out.println(gfoMoneySplitVO.getUnits());
        System.out.println(gfoMoneySplitVO.getNanos());


    }

    /***
     * 除以100
     * @param number
     * @return
     */
    public static BigDecimal divideOneHundred(Integer number) {
        return BigDecimal.valueOf(number)
                .divide(BigDecimal.valueOf(100L), 2, 4);
    }

    /***
     * 除以10
     * @param number
     * @return
     */
    public static BigDecimal divideTen(Integer number) {
        return BigDecimal.valueOf(number)
                .divide(BigDecimal.valueOf(10L), 2, 4);
    }


    /**
     * 获取 整数 num 的第 i 位的值(从右到左)，true 表示第i位为1,否则为0
     * @param num  整数
     * @param i 第 i 位
     * @return
     */
    public static boolean getBit(int num, int i) {
        if (i <= 0){
            i = 1;
        }
        return ((num & (1 << (i - 1))) != 0);
    }

    /**
     * 保留两位小数
     *
     * @param value
     * @return
     */
    public static String getNumber(double value) {
        return new Formatter().format("%.2f", value).toString();
    }


    public static Integer safeToInteger(Integer value) {
        return Optional.ofNullable(value).orElse(0);
    }

    public static Long safeToLong(Long value) {
        return Optional.ofNullable(value).orElse(0L);
    }
    public static Long safeToLong(String value, Long defaultVal) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return defaultVal;
        }
    }
    public static Integer getIntegerByByte(Byte b) {
        return Optional.ofNullable(b).map(Integer::valueOf).orElse(null);
    }

    public static Integer getIntegerByBoolean(Boolean b) {
        if (b == null) {
            return null;
        }
        if (b) {
            return NumberUtils.INTEGER_ONE;
        }
        return NumberUtils.INTEGER_ZERO;
    }

    public static Double getDoubleByBigDecimal(BigDecimal b) {
        return Optional.ofNullable(b).map(BigDecimal::doubleValue).orElse(null);
    }

    public static String getStringByDouble(Double d) {
        return Optional.ofNullable(d).map(String::valueOf).orElse(null);
    }

    public static Integer getIntegerByLong(Long l) {
        if (l == null) {
            return null;
        }
        if (l > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return l.intValue();
    }

    public static Integer getIntegerByLongToNull(Long l) {
        if (l == null) {
            return null;
        }
        if (l > Integer.MAX_VALUE) {
            return null;
        }
        return l.intValue();
    }

    public static Integer getIntegerByBigDecimal(BigDecimal b){
        return Optional.ofNullable(b).map(BigDecimal::intValue).orElse(null);
    }

    public static int getInt(Number number, int defalut) {
        if (null == number) {
            return defalut;
        }
        return number.intValue();
    }

    public static double getDouble(Number number, double defalut) {
        if (null == number) {
            return defalut;
        }
        return number.doubleValue();
    }

    /**
     * 是否包含数字
     * @param ret
     * @return
     */
    public static boolean containsNumber(String ret) {
        return NUMBER_PATTERN.matcher(ret).matches();
    }

    public static Long getLong(String num) {
        if(StringUtils.isBlank(num)) return 0L;
        try {
            return Long.parseLong(num);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static Integer getInt(String num) {
        if(StringUtils.isBlank(num)) return 0;
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean checkNullAndLT0(Long ... objs){
        if (objs.length <= 0){
            return true;
        }
        for (Long num : objs) {
            if (Objects.isNull(num) || num <= 0){
                return true;
            }
        }
        return false;
    }

    public static boolean checkNumGL0(Object num){
        if (Objects.isNull(num)){
            return false;
        }
        if (num instanceof Integer || num instanceof Long || num instanceof Short || num instanceof Byte){
            return ((Number) num).longValue() > 0;
        } else if (num instanceof BigDecimal || num instanceof Float || num instanceof Double){
            return ((Number) num).doubleValue() > 0;
        } else {
            return false;
        }
    }
}
