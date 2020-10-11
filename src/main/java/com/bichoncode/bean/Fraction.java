package com.bichoncode.bean;

import com.bichoncode.utils.RandomUtils;

/**
 * @author BichonCode
 * @mail chenzhichaohh@163.com
 * @create 2020/10/10
 */
public class Fraction {

    // 分子
    private int numerator;
    // 分母，不能为0，默认为1
    private int denominator = 1;

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    // 设置分子和分母
    public Fraction(int numerator, int denominator) {
        setFactionValue(numerator, denominator);
    }

    // 通过表达式得到分子和分母,都未经过简化，分母可能为0
    public Fraction(String result) {
        result.trim();
        int numerator_index = result.indexOf("/");
        int numerator1_index = result.indexOf("'");

        // 不是分式的时候
        if (numerator_index == -1) {
            numerator = Integer.valueOf(result);
        }
        // 是分式的时候
        else {
            // 分母
            denominator = Integer.valueOf(result.substring(numerator_index + 1));
            // 真分数
            if (numerator1_index == -1) {
                numerator = Integer.valueOf(result.substring(0, numerator_index));
            }
            // 带分数
            else {
                int numerator1 = Integer.valueOf(result.substring(0, numerator1_index));
                int numerator0 = Integer.valueOf(result.substring(numerator1_index + 1, numerator_index));
                numerator = numerator1 * denominator + numerator0;
            }
        }
        setFactionValue(numerator, denominator);
    }

    // 将分子分母调整之后，存储到成员变量中
    public void setFactionValue(int numerator, int denominator) {
        if (denominator == 0)
            throw new RuntimeException("分母不能为0");
        // 结果默认是正数
        int isNagitiveAB = 1;
        // 调整符号，b只能为正数
        if (numerator * denominator < 0) {
            isNagitiveAB = -1;
        }
        numerator = Math.abs(numerator);
        denominator = Math.abs(denominator);
        // 最大公因数
        int g = gcd(numerator, denominator);
        // 化简
        this.numerator = numerator * isNagitiveAB / g;
        this.denominator = denominator / g;

    }



    public static Fraction generateFraction() {
        // a.b 都是大于等于0的
        int numerator = RandomUtils.getARandom(Expression.range);
        int denominator = RandomUtils.getARandom(Expression.range);
        // 分母为0
        while (denominator == 0) {
            denominator = RandomUtils.getARandom(Expression.range);
        }
        Fraction result = new Fraction(numerator, denominator);
        return result;
    }


    // 加法
    public Fraction plus(Fraction right) {
        // a/b+c/d =（ad+bc）/bd
        return new Fraction(
                this.numerator * right.denominator + this.denominator * right.numerator,
                this.denominator * right.denominator
        );
    }

    // 减法
    public Fraction subtract(Fraction right) {
        // a/b-c/d =（ad-bc）/bd
        return new Fraction(
                this.numerator * right.denominator - this.denominator * right.numerator,
                this.denominator * right.denominator
        );
    }

    // 乘法
    public Fraction multiply(Fraction right) {
        // a/b * c/d = ac / bd
        return new Fraction(
                this.numerator * right.numerator,
                this.denominator * right.denominator
        );
    }

    // 除法
    public Fraction divide(Fraction right) {
        // a/b  /  c/d = ad / bc
        return new Fraction(
                this.numerator * right.denominator,
                this.denominator * right.numerator
        );
    }


    // 辗转相除法，求最大公约数
    private int gcd(int numerator, int denominator) {
        int big = numerator;
        if (big == 0)
            return 1;
        int small = denominator;
        //让a成为最大的
        if (numerator < denominator) {
            big = denominator;
            small = numerator;
        }
        int mod = big % small;
        return mod == 0 ? small : gcd(small, mod);
    }


    // 看当前分数是否为负数
    boolean isNegative() {
        // 结果默认是正数
        boolean isNagitiveFraction = false;
        if (numerator * denominator < 0) {
            isNagitiveFraction = true;
        }
        return isNagitiveFraction;
    }


    // 将分子分分母转化成手写形式
    @Override
    public String toString() {
        //不是分式
        if (denominator == 1)
            return String.valueOf(numerator);
            //真分式
        else {
            int i = numerator / denominator;
            //余数
            int j = numerator % denominator;

            // 分母为0则直接返回0
            if (numerator == 0) {
                return String.format("%d", 0);
            }
            if (i != 0) {
                return String.format("%d'%d/%d", i, j, denominator);
            } else {
                return String.format("%d/%d", numerator, denominator);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        if (numerator != fraction.numerator) return false;
        return denominator == fraction.denominator;
    }

    // 重写hashcode
    @Override
    public int hashCode() {
        int result = 31 * numerator + denominator;
        return result;
    }



}

