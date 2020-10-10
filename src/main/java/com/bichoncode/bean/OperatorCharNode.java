package com.bichoncode.bean;


/**
 * 操作运算符节点
 * @author BichonCode
 * @mail chenzhichaohh@163.com
 * @create 2020/10/10
 */
public class OperatorCharNode extends BiTreeNode {

    // 运算符
    public String operator;

    public OperatorCharNode(BiTreeNode left, BiTreeNode right, String operator) {
        // 父类中无用的常量设置为null
        super(null, left, right, 0);
        this.operator = operator;
    }


    // 中间节点存放运算符，按需求，用空格隔开
    @Override
    public String toString() {
        return " " + operator + " ";
    }
}
