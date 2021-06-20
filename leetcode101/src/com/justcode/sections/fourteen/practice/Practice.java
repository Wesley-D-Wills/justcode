package com.justcode.sections.fourteen.practice;

import com.justcode.sections.fourteen.Tree;
import com.justcode.sections.fourteen.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * **********************************************
 * 练习题-基础难度
 ************************************************/
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
     * 1. 对于二叉搜索树，从上到下搜索如果第一次遇见 x< cur < y 基本上就是最近公共祖先
     * 如果cur < x < y 则遍历右子树
     * 否则遍历左子树
     * 2. 对于一般的二叉树，则遍历树的路径，找到包含两个节点的路径，返回其最近公共祖先
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

    /**
     * LeetCode-236
     * 二叉树的最近公共祖先
     * 1. 对于二叉搜索树，从上到下搜索如果第一次遇见 x< cur < y 基本上就是最近公共祖先
     * 如果cur < x < y 则遍历右子树
     * 否则遍历左子树
     * 2. 对于一般的二叉树，则遍历树的路径，找到包含两个节点的路径，返回其最近公共祖先
     */
    List<TreeNode> pathAns = null;
    public TreeNode lowestCommonAncestor236(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> path1 = null;
        List<TreeNode> path2 = null;
        commonHelper236(root, p, new ArrayList<>(), 0); // contains 表示一个路径上目前已经包含多少个节点了
        path1 = new ArrayList<>(pathAns);
        pathAns.clear();
        commonHelper236(root, q, new ArrayList<>(), 0); // contains 表示一个路径上目前已经包含多少个节点了
        path2 = new ArrayList<>(pathAns);
        int index = 0;
        TreeNode node = null;
        while (index < path1.size() && index < path2.size()) {
            if (path1.get(index).equals(path2.get(index))) {
                node = path1.get(index);
            } else {
                break;
            }
        }
        return node;
    }

    private void commonHelper236(TreeNode root, TreeNode node, List<TreeNode> path, int contains) {
        if (root == null) {
            if (contains != 0) {
                pathAns = new ArrayList<>(path);
            }
            return;
        }
        path.add(root);
        if (root.val == node.val) {
            contains++;
        }
        commonHelper236(root.left, node, path, contains);
        commonHelper236(root.right, node, path, contains);
        path.remove(path.size() - 1);
    }

    /**
     * LeetCode-530
     * 二叉搜索树的最小绝对差
     */
    int min = Integer.MAX_VALUE;
    TreeNode pre;

    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 中序遍历
        minHelper(root);
        return min;
    }

    private void minHelper(TreeNode root) {
        if (root == null) {
            return;
        }
        minHelper(root.left);
        if (pre != null) {
            min = Math.min(Math.abs(pre.val - root.val), min);
        }
        pre = root;
        minHelper(root.right);
    }
}
