package com.bichoncode.bean;


import com.bichoncode.exception.CommonException;
import com.bichoncode.utils.RandomUtils;

/**
 * @author BichonCode
 * @mail chenzhichaohh@163.com
 * @create 2020/10/10
 */
public class Expression {

    private String PLUS = OperationalCharEnum.PlUS.getValueChar();

    private String SUBTRACT = OperationalCharEnum.SUBTRACT.getValueChar();

    private String MULTIPLY = OperationalCharEnum.MULTIPLY.getValueChar();

    private String DIVIDE = OperationalCharEnum.DIVIDE.getValueChar();

    private String LEFT_BRACKETS = OperationalCharEnum.LEFT_BRACKETS.getValueChar();

    private String RIGHT_BRACKETS = OperationalCharEnum.RIGHT_BRACKETS.getValueChar();

    // 运算符的种类
    private String[] OPERATORS = {PLUS, SUBTRACT, MULTIPLY, DIVIDE};

    // 二叉树的根节点
    private BiTreeNode root;

    // 是否出现除数为0的情况
    private boolean divisorIsZero = false;

    public boolean divisorIsZero() {
        return divisorIsZero;
    }

    public void setdivisorIsZero(boolean divisorIsZero) {
        divisorIsZero = divisorIsZero;
    }

    // 生成答案的范围
    public static int range;

    public BiTreeNode getRoot() {
        return root;
    }

    public void setRoot(BiTreeNode root) {
        this.root = root;
    }

    // 生成四则运算表达式
    public Expression(int operator_number, int answer_range) {
        if (operator_number < 1) {
            throw new CommonException("运算符个数必须大于0");
        }
        if (answer_range < 1) {
            throw new RuntimeException("运算结果范围必须大于等于1");
        }
        this.range = answer_range;

        if (operator_number == 1) {
            root = generateNode(operator_number);
        } else {
            root = generateNode(RandomUtils.getARandom(operator_number) + 1);
        }
    }


    /**
     * 构建生成四则运算表达式的二叉树
     *
     * @Param number 运算符的个数
     **/
    public BiTreeNode generateNode(int number) {
        // 如果是0就构造叶子节点
        if (number == 0) {
            return new BiTreeNode(Fraction.generateFraction(), null, null, 1);
        }
        // 其他都是构造符号节点
        OperatorCharNode parent = new OperatorCharNode(null, null, OPERATORS[RandomUtils.getARandom(4)]);
        int left = RandomUtils.getARandom(number);
        // 递归下去构造左孩子和右孩子
        parent.left = generateNode(left);
        // 总数要减去当前已经构建出来的这一个节点
        parent.right = generateNode(number - 1 - left);

        // 然后计算结果
        Fraction result = calculate(parent.operator, parent.left.result, parent.right.result);
        // 如果是负数,就是出现小的减去大的情况，这时候交换左右孩子
        if (result.isNegative()) {
            BiTreeNode tmp = parent.left;
            parent.left = parent.right;
            parent.right = tmp;
        }
        // 重新计算结果
        parent.result = calculate(parent.operator, parent.left.result, parent.right.result);
        // 计算树高
        parent.high = Math.max(parent.left.high, parent.right.high) + 1;
        return parent;
    }


    // 进行两个元素的计算
    private Fraction calculate(String operator, Fraction leftFraction, Fraction rightFraction) {
        if (operator.equals(PLUS))
            return leftFraction.plus(rightFraction);
        else if (operator.equals(SUBTRACT))
            return leftFraction.subtract(rightFraction);
        else if (operator.equals(MULTIPLY))
            return leftFraction.multiply(rightFraction);
        else if (operator.equals(DIVIDE)) {
            //可能会出现除以0的情况，即rightFraction可能为0
            if (rightFraction.getNumerator() == 0) {
                this.divisorIsZero = true;
                rightFraction.setNumerator(1);
            }
            return leftFraction.divide(rightFraction);
        } else
            throw new RuntimeException("该操作符不存在");

    }

    // 打印四则运算表达式
    @Override
    public String toString() {
        return print(root);
    }

    /**
     * 中序遍历二叉树,左中右
     *
     * @Param localRootNode 当前所在的最高节点，可以不是根节点
     * @Return java.lang.String
     **/
    private String print(BiTreeNode localRootNode) {

        if (localRootNode == null) {
            return "";
        }
        String left = print(localRootNode.left);
        String mid = localRootNode.toString();
        // 需要加括号的情况,一个节点的操作符为乘除，其子节点的操作符是加减
        if (localRootNode.left instanceof OperatorCharNode && localRootNode instanceof OperatorCharNode) {
            if (leftBrackets(((OperatorCharNode) localRootNode.left).operator, ((OperatorCharNode) localRootNode).operator)) {
                left = LEFT_BRACKETS + " " + left + " " + RIGHT_BRACKETS;
            }
        }
        String right = print(localRootNode.right);
        if (localRootNode.right instanceof OperatorCharNode && localRootNode instanceof OperatorCharNode) {
            if (rightBrackets(((OperatorCharNode) localRootNode.right).operator, ((OperatorCharNode) localRootNode).operator)) {
                right = LEFT_BRACKETS + " " + right + " " + RIGHT_BRACKETS;
            }
        }
        return left + mid + right;
    }


    // 向左遍历时，需要括号
    private boolean leftBrackets(String left, String mid) {
        return (isAddOrSubtract(left) && isMultiplyOrDivide(mid));
    }

    // 向右遍历时，需要括号
    private boolean rightBrackets(String right, String mid) {
        //有可能出现2*3 /( 4*5 )的情况，所以不用加括号只有当
        return !(isAddOrSubtract(mid) && isMultiplyOrDivide(right));
    }

    /**
     * 是加减运算符
     *
     * @Param operator
     * @Return boolean
     **/
    private boolean isAddOrSubtract(String operator) {
        return operator.equals(PLUS) || operator.equals(SUBTRACT);
    }

    /**
     * 是乘除运算符
     *
     * @Param operator
     * @Return boolean
     **/
    private boolean isMultiplyOrDivide(String operator) {
        return operator.equals(MULTIPLY) || operator.equals(DIVIDE);
    }


}

