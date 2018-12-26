/**
 * Copyright  2015  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Description: RandomUtil
 * @Author: estn.zuo
 * @CreateTime: 2015-09-09 15:34
 */
public class RandomUtil {

    private static int DEFAULT_SCALE = 0;

    private RandomUtil() {
    }

    public static Integer generateEven(int min, int max) {
        return generateEven(min, max, DEFAULT_SCALE).intValue();
    }

    /**
     * 生成偶数
     *
     * @param minDouble 最小值
     * @param maxDouble 最大值
     * @return
     */
    public static Double generateEven(double minDouble, double maxDouble, int scale) {
        int min = (int) (minDouble * Math.pow(10, scale));
        int max = (int) (maxDouble * Math.pow(10, scale));
        Integer result = new Random().nextInt(max - min);
        result = result + min;

        if (result % 2 != 0) {
            result = result + 1;
        }

        if (result > max) {
            result = result - 2;
        }

        if (result < min) {
            result = result + 2;
        }

        //计算精度
        if (scale != 0) {
            return ArithmeticUtil.round(result / Math.pow(10, scale), scale);
        }
        return result * 1.0;
    }

    public static Integer generateOdd(int min, int max) {
        return generateOdd(min, max, DEFAULT_SCALE).intValue();
    }

    /**
     * 生成奇数
     *
     * @param minDouble 最小值
     * @param maxDouble 最大值
     * @return
     */
    public static Double generateOdd(int minDouble, int maxDouble, int scale) {
        int min = (int) (minDouble * Math.pow(10, scale));
        int max = (int) (maxDouble * Math.pow(10, scale));
        Integer result = new Random().nextInt(max - min);
        result = result + min;

        //确保result为基数
        if (result % 2 != 1) {
            result = result + 1;
        }
        if (result > max) {
            result = result - 2;
        }
        if (result < min) {
            result = result + 2;
        }

        //计算精度
        if (scale != 0) {
            return ArithmeticUtil.round(result / Math.pow(10, scale), scale);
        }
        return result * 1.0;
    }

    public static Integer generate(int min, int max) {
        return generate(min, max, DEFAULT_SCALE).intValue();
    }

    /**
     * 生成随机
     *
     * @param minDouble 最小值
     * @param maxDouble 最大值
     * @return
     */
    public static Double generate(double minDouble, double maxDouble, int scale) {
        int min = (int) (minDouble * Math.pow(10, scale));
        int max = (int) (maxDouble * Math.pow(10, scale));
        Integer result = new Random().nextInt(max - min);
        result = result + min;

        if (result > max) {
            result = result - 1;
        }
        if (result < min) {
            result = result + 1;
        }

        if (scale != 0) {
            return round(result / Math.pow(10, scale), scale);
        }
        return result * 1.0;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    private static Double round(double v, int scale) {

        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
