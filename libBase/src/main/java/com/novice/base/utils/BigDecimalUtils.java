package com.novice.base.utils;

import java.math.BigDecimal;

/**
 * @author novice
 */
public class BigDecimalUtils {

    private static final int DEF_DIV_SCALE = 2; //保留小数点后面的位数

    /**
     * 将数值转为保存两位小数点
     * @return
     */
    public static BigDecimal format(BigDecimal v1){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        return b1.setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 将数值转为保存指定小数点
     * @return
     */
    public static BigDecimal format(BigDecimal v1, int scale){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        return b1.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        BigDecimal b2 = v2 == null ? new BigDecimal(0) : v2;
        return b1.add(b2).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2, int scale){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        BigDecimal b2 = v2 == null ? new BigDecimal(0) : v2;
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal v1,BigDecimal v2){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        BigDecimal b2 = v2 == null ? new BigDecimal(0) : v2;
        return b1.subtract(b2).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal v1,BigDecimal v2, int scale){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        BigDecimal b2 = v2 == null ? new BigDecimal(0) : v2;
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1,BigDecimal v2){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        BigDecimal b2 = v2 == null ? new BigDecimal(0) : v2;
        return b1.multiply(b2).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1,BigDecimal v2, int scale){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        BigDecimal b2 = v2 == null ? new BigDecimal(0) : v2;
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1,BigDecimal v2){
        BigDecimal b1 = v1 == null ? new BigDecimal(0) : v1;
        BigDecimal b2 = v2 == null ? new BigDecimal(0) : v2;
        return div(b1,b2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param b1 被除数
     * @param b2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal b1,BigDecimal b2,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
    }

}
