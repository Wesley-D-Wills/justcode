package com.justcode.sections.thirteen;

import com.justcode.sections.fourteen.TreeNode;

import java.util.HashSet;
import java.util.Set;

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

    /**
     * LeetCode-24
     * 两两交换链表中的节点
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode ans = head.next;
        ListNode pre = head;

        ListNode next = head.next.next;
        head.next.next = head;
        head.next = next;

        ListNode cur = next;
        while (cur != null && cur.next != null) {
            pre.next = cur.next;
            pre = cur;

            next = cur.next.next;
            cur.next.next = cur;
            cur.next = next;
            cur = next;
        }
        return ans;
    }

    /** ***********************************************
     * 其他链表技巧
     * ************************************************/
    /**
     * LeetCode-160
     * 1. 相交链表
     * 2. 比较耗费空间的做法：hash集合
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode curA = headA;
        ListNode curB = headB;
        while (curA != curB) {
            if (curA == null) {
                curA = headB;
            } else {
                curA = curA.next;
            }
            if (curB == null) {
                curB = headA;
            } else {
                curB = curB.next;
            }
        }
        return curA;
    }

    // hash集合
    public ListNode getIntersectionNodeSet(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }

    /**
     * LeetCode-234
     * 1. 回文链表
     *      快慢指针的方法
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 翻转链表
        ListNode curN = slow.next;
        ListNode pre = null;
        while (curN != null) {
            ListNode next = curN.next;
            curN.next = pre;
            pre = curN;
            curN = next;
        }
        while (pre != null && head != null) {
            if (pre.val != head.val) {
                return false;
            }
            pre = pre.next;
            head = head.next;
        }
        return true;
    }
}
