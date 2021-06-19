package com.justcode.sections.fourteen.practice;

import com.justcode.sections.fourteen.Tree;
import com.justcode.sections.fourteen.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/** ***********************************************
 * 练习题
 * ************************************************/
public class Practice {
    /**
     * LeetCode-226
     * 翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return root;
        }
        return invertHelp(root, root.left, root.right);
    }

    private TreeNode invertHelp(TreeNode root, TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return root;
        }
        if (left != null) {
            invertHelp(left, left.left, left.right);
        }
        if (right != null) {
            invertHelp(right, right.left, right.right);
        }
        root.left = right;
        root.right = left;
        return root;
    }

    /**
     * LeetCode-617
     * 合并二叉树
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        root1.val = root1.val + root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    /**
     * LeetCode-572
     * 合并二叉树
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) {
            return true;
        }
        if (root == null || subRoot == null) {
            return false;
        }
        boolean flag = false;
        if (root.val == subRoot.val) {
            flag = isEqual(root, subRoot);
        }
        return flag || isSubtree(root.left, subRoot)
                || isSubtree(root.right, subRoot);
    }

    private boolean isEqual(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) {
            return true;
        }
        if (root == null || subRoot == null) {
            return false;
        }
        if (root.val != subRoot.val) {
            return false;
        }
        return isEqual(root.left, subRoot.left)
                && isEqual(root.right, subRoot.right);
    }

    /**
     * LeetCode-404
     * 左叶子节点之和
     */
    int sum = 0;
    public int sumOfLeftLeaves(TreeNode root) {
        dfs(root, false);
        return sum;
    }

    private void dfs(TreeNode root, boolean b) {
        if (root == null) {
            return;
        }
        // boolean b 表示是否是左节点
        if (root.left == null && root.right == null && b) {
            sum += root.val;
        }
        dfs(root.left, true);
        dfs(root.right, false);
    }

    /**
     * LeetCode-513
     * 找树左下角的值
     */
    public int findBottomLeftValue(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offerLast(root);
        TreeNode mostLeft = null;
        while (!queue.isEmpty()) {
            int size = queue.size();
            mostLeft = queue.peekFirst();
            while (size > 0) {
                TreeNode node = queue.pollFirst();
                if (node.left != null) {
                    queue.offerLast(node.left);
                }
                if (node.right != null) {
                    queue.offerLast(node.right);
                }
                size--;
            }
        }
        return mostLeft.val;
    }

    /**
     * LeetCode-538
     * 把二叉搜索树转换为累加树
     */
    int preSum = 0;
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }
        convertBST(root.right);
        root.val = preSum + root.val;
        preSum = root.val;
        convertBST(root.left);
        return root;
    }
    // Morris算法
    public TreeNode convertBST1(TreeNode root) {
        TreeNode cur = root;
        TreeNode mostLeft = null;
        while (cur != null) {
            mostLeft = cur.right;
            if (mostLeft != null) {
                while (mostLeft.left != null && mostLeft.left != cur) {
                    mostLeft = mostLeft.left;
                }
                if (mostLeft.left == null) {
                    mostLeft.left = cur;
                    cur = cur.right;
                    continue;
                } else {
                    cur.val = preSum + cur.val;
                    preSum = cur.val;
                    mostLeft.left = null;
                }
            } else {
                cur.val = preSum + cur.val;
                preSum = cur.val;
            }
            cur = cur.left;
        }
        return root;
    }

    /**
     * LeetCode-235
     * 二叉搜索树的最近公共祖先
     */
    TreeNode ans = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == p || root == q) {
            return root;
        }
        ans = root;
        if (p.val < q.val) {
            commonHelper(root, p, q);
        } else {
            commonHelper(root, q, p);
        }
        return ans;
    }

    private void commonHelper(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return;
        }
        if (root.val < p.val) {
            ans = root.right;
            commonHelper(root.right, p, q);
            return;
        }
        if (root.val > q.val) {
            ans = root.left;
            commonHelper(root.left, p, q);
        }
    }
}
