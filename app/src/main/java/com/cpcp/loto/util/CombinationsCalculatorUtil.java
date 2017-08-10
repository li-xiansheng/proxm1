package com.cpcp.loto.util;

/**
 * 功能描述：排列组合计算类，用于计算排列组合值
 */

public class CombinationsCalculatorUtil {

    /**
     * A m选n组合，不排列返回格式
     *
     * @param m
     * @param n
     * @return
     */
    public static long Combinations(int m, int n) {
        if (m < n) {
            return 0;
        }
        return factorial(m) / (factorial(n) * factorial(m - n));
    }

    /**
     * 使用递归方法计算n的阶乘
     *
     * @param n
     * @return
     */
    public static long factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            long num = n * factorial(n - 1);

            return num;
        }
    }
}

