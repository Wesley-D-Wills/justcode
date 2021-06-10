package com.justcode.sections.fourteen;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 每个函数使用 LeetCode-xxx 表示是那一道题
 */
public class Tree {
    /** ***********************************************
     * 树的递归
     * ************************************************/
    /**
     * LeetCode-1110
     * 删点成林：采用的后根遍历的形式，同时返回当前节点
     * 1. 首先考虑从上往下（或者中序遍历）删除，这样考虑肯定是不妥的因为删除当前节点，
     * 你也无法将其左子树的根节点或者右子树的根节点加入结果列表中
     * 2. 因而考虑后跟遍历的形式，这样每次遍历当前节点是否要删除时，直接可以将其左子树的根节点或者右子树的根节点
     * 加入到结果集中，同时要讲当前节点置为null返回，修改节点连接。
     */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Set<Integer> set = Arrays.stream(to_delete).boxed().collect(Collectors.toSet());
        TreeNode node = helper(root, ans, set);
        if (node != null) {
            ans.add(node);
        }
        return ans;
    }

    private TreeNode helper(TreeNode node, List<TreeNode> ans, Set<Integer> set) {
        if (node == null) {
            return null;
        }
        node.left = helper(node.left, ans, set);
        node.right = helper(node.right, ans, set);
        if (set.contains(node.val)) {
            if (node.left != null) {
                ans.add(node.left);
            }
            if (node.right != null) {
                ans.add(node.right);
            }
            node = null;
        }
        return node;
    }

    /** ***********************************************
     * 层次遍历
     * ************************************************/
    /**
     * LeetCode-637 easy
     * 防止sum超过int的最大值，可以一开始使用double类型的sum
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            double sum = 0;
            int count = size;
            while (size > 0) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                size--;
            }
            ans.add(sum / count);
        }
        return ans;
    }
    /** ***********************************************
     * 前中后序遍历
     * ************************************************/
    /**
     * LeetCode-105 medium
     * 构造树的过程是从上往下进行构造类似于先根遍历
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        TreeNode root = helper(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1, map);
        return root;
    }

    private TreeNode helper(int[] preorder, int[] inorder, int pl, int pr, int il, int ir, Map<Integer, Integer> map) {
        if (pl > pr) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[pl]);
        int length = map.get(preorder[pl]) - il;
//        root.left = helper(preorder, inorder, pl + 1, pl + length, il, map.get(preorder[pl]) - 1, map);
        root.left = helper(preorder, inorder, pl + 1, pl + length, il, il + length - 1, map);
        root.right = helper(preorder, inorder, pl + length + 1, pr, il + length + 1, ir, map);
        return root;
    }

    /**
     * LeetCode-105 easy
     * 使用迭代的方式 完成先根遍历
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            ans.add(node.val);
            if (node.right != null) {
                queue.offerFirst(node.right);
            }
            if (node.left != null) {
                queue.offerFirst(node.left);
            }
        }
        return ans;
    }
}

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

