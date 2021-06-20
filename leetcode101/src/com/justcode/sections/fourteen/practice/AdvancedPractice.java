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

    /**
     * LeetCode-897
     * 递增顺序搜索树
     */
    public TreeNode increasingBST(TreeNode root) {
        if (root == null) {
            return root;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode head = null;
        TreeNode pre = null;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.offerLast(root);
                root = root.left;
            }
            root = stack.pollLast();
            if (head == null) {
                head = root;
            }
            if (pre != null) {
                pre.right = root;
            }
            pre = root;
            // pre.right = null;
            pre.left = null;
            root = root.right;
        }
        return head;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(3);
        AdvancedPractice advancedPractice = new AdvancedPractice();
        advancedPractice.increasingBST(root);
    }

    /**
     * LeetCode-653
     * 两数之和 IV - 输入 BST
     */
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return dfs(root, k, set);
    }

    private boolean dfs(TreeNode root, int k, Set<Integer> set) {
        if (root == null) {
            return false;
        }
        int val = root.val;
        if (set.contains(val)) {
            return true;
        }
        set.add(k - val);
        return dfs(root.left, k, set) ||
                dfs(root.right, k, set);
    }

    /**
     * LeetCode-450
     * 删除二叉搜索树中的节点
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            TreeNode most = root.right;
            TreeNode move = root.left.right;
            root.left.right = most;
            while (most.left != null) {
                most = most.left;
            }
            most.left = move;
            return root.left;
        }
        root.left = deleteNode(root.left, key);
        root.right = deleteNode(root.right, key);
        return root;
    }
}
