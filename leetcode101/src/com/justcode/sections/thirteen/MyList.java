package com.justcode.sections.thirteen;

import com.justcode.sections.fourteen.TreeNode;

public class MyList {
    /** ***********************************************
     * 链表的基本操作
     * ************************************************/
    /**
     * LeetCode-206
     * 翻转一个链表
     * 1. 非递归操作
     * 2. 递归操作
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode pre = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    // 递归操作
    private ListNode preDfs = null;
    public ListNode reverseListDfs(ListNode head) {
        if (head == null) {
            return preDfs;
        }
        ListNode next = head.next;
        head.next = preDfs;
        preDfs.next = head;
        return reverseListDfs(next);
    }

    /**
     * LeetCode-21
     * 合并两个有序链表
     * 1. 非递归操作
     * 2. 递归操作
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                cur.next = l1;
                cur = cur.next;
                l1 = l1.next;
            } else {
                cur.next = l2;
                cur = cur.next;
                l2 = l2.next;
            }
        }
        if (l1 == null) {
            cur.next = l2;
        }
        if (l2 == null) {
            cur.next = l1;
        }
        return head.next;
    }

    // 递归算法
    private ListNode head = new ListNode(0);
    public ListNode mergeTwoListsDfs(ListNode l1, ListNode l2) {
        ListNode cur = head;
        dfs(l1, l2, cur);
        return head.next;
    }

    private void dfs(ListNode l1, ListNode l2, ListNode cur) {
        if (l1 == null) {
            cur.next = l2;
            return;
        }
        if (l2 == null) {
            cur.next = l1;
            return;
        }
        if (l1.val <= l2.val) {
            cur.next = l1;
            l1 = l1.next;
        } else {
            cur.next = l2;
            l2 = l2.next;
        }
        dfs(l1, l2, cur.next);
    }
}
