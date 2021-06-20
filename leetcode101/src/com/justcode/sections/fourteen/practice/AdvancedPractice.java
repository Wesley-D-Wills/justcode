package com.justcode.sections.fourteen.practice;

import com.justcode.sections.fourteen.Tree;
import com.justcode.sections.fourteen.TreeNode;

import java.util.*;

/**
 * **********************************************
 * 练习题-进阶难度
 ************************************************/
public class AdvancedPractice {
    /**
     * LeetCode-889
     * 根据前序和后序遍历构造二叉树
     */
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < post.length; i++) {
            map.put(post[i], i);
        }

        TreeNode root = buildTree(pre, post, 0, pre.length - 1, 0, post.length - 1, map);

        return root;
    }

    private TreeNode buildTree(int[] pre, int[] post, int i, int length, int i1, int length1, Map<Integer, Integer> map) {
        if (i > length) {
            return null;
        }
        TreeNode root = new TreeNode(pre[i]); // 创建子树根节点
        if (i + 1 > length) {
            int index = map.get(pre[i + 1]);
            int offset = index - i1 + 1;
            root.left = buildTree(pre, post, i + 1, i + offset, i1, index, map);
            root.right = buildTree(pre, post, i + offset + 1, length, index + 1, length1 - 1, map);
            return root;
        }
        return root;
    }

    /**
     * LeetCode-106
     * 从中序与后序遍历序列构造二叉树
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        int pl = 0;
        int pr = inorder.length - 1;
        int il = 0;
        int ir = inorder.length - 1;
        TreeNode root = buildTreeHelper(inorder, postorder, pl, pr, il, map);
        return root;
    }

    private TreeNode buildTreeHelper(int[] inorder, int[] postorder, int pl, int pr, int il, Map<Integer, Integer> map) {
        if (pl > pr) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[pr]);
        int index = map.get(postorder[pr]);
        int offset = index - il - 1;
        root.left = buildTreeHelper(inorder, postorder, pl, pl + offset, il, map);
        root.right = buildTreeHelper(inorder, postorder, pl + offset + 1, pr - 1, index + 1, map);
        return root;
    }

    /**
     * LeetCode-94
     * 二叉树的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.left;
            }
            cur = stack.pollLast();
            ans.add(cur.val);
            cur = cur.right;
        }
        return ans;
    }

    // Morris算法
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode cur = root;
        TreeNode mostRight;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    ans.add(cur.val);
                    mostRight.right = null;
                }
            } else {
                ans.add(cur.val);
            }
            cur = cur.right;
        }
        return ans;
    }

    /**
     * LeetCode-145
     * 二叉树的后序遍历
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        TreeNode pre = null;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.left;
            }
            cur = stack.pollLast();
            if (cur.right == null || cur.right == pre) {
                ans.add(cur.val);
                pre = cur;
                cur = null;
            } else {
                stack.offerLast(cur);
                cur = cur.right;
            }
        }
        return ans;
    }
}
