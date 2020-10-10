package com.bichoncode.bean;

/**
 * 操作符枚举类
 * 包括 + - * / （ ）
 * @author BichonCode
 * @mail chenzhichaohh@163.com
 * @create 2020/10/09
 */
public enum OperationalCharEnum {

    PlUS("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    LEFT_BRACKETS("("),
    RIGHT_BRACKETS(")");
    // 操作字符
    private String valueChar;

    OperationalCharEnum(String valueChar) {
        this.valueChar = valueChar;
    }

    public String getValueChar() {
        return valueChar;
    }

    public void setValueChar(String valueChar) {
        this.valueChar = valueChar;
    }
}
