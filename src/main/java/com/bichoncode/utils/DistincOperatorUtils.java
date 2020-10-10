package com.bichoncode.utils;

import com.bichoncode.bean.BiTreeNode;

/**
去重工具类
 */
public class DistincOperatorUtils {

    /**
     * 使用递归判断两科二叉树是否同构
     * @param root1
     * @param root2
     * @return
     */
    public static boolean isomorphism(BiTreeNode root1, BiTreeNode root2) {
        if (root1 == null && root2 == null)
            return true;
        if (root1 == null && root2 != null) {
            return false;
        }
        if (root1 != null && root2 == null) {
            return false;
        }
        if (!root1.result.equals(root2.result) )
            return false;
        return isomorphism(root1.left, root2.left) && isomorphism(root1.right, root2.right)
                ||isomorphism(root1.left, root2.right) && isomorphism(root1.right, root2.left);

    }


}
