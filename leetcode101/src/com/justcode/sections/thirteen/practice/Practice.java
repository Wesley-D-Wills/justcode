package com.justcode.sections.thirteen.practice;

import com.justcode.sections.thirteen.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class Practice {
    /**
     * **********************************************
     * 练习题-基础难度
     ************************************************/
    /**
     * LeetCode-83
     * 删除排序链表中的重复元素
     * 1. 利用前置节点
     * 2. 直接利用节点的特性
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        int val = head.val;
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                val = cur.val;
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    /**
     * LeetCode-328
     * 奇偶链表
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode odd = head;        // 奇数
        ListNode even = head.next;  // 偶数
        ListNode headEven = even;
        while (even.next != null) {
            ListNode oddNext = even.next;
            odd.next = oddNext;
            odd = oddNext;

            if (odd.next != null) {
                ListNode evenNext = odd.next;
                even.next = evenNext;
                even = evenNext;
            } else {
                even.next = null;
            }
        }
        odd.next = headEven;
        return head;
    }

    /**
     * LeetCode-19
     * 删除链表的倒数第 N 个结点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        // 快慢指针
        while (n > 0 && fast != null) {
            fast = fast.next;
            n--;
        }
        if (n > 0) {
            return null;
        }
        if (fast == null) {
            return head.next;
        }
        ListNode cur = head;
        ListNode pre = null;
        while (fast != null) {
            pre = cur;
            cur = cur.next;
            fast = fast.next;
        }
        pre.next = cur.next;
        return head;
    }

    // 快慢指针
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummyHead = new ListNode(0, head);
        ListNode fast = head;
        ListNode slow = dummyHead;
        while (n > 0) {
            fast = fast.next;
            n--;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummyHead.next;
    }

    // 栈
    public ListNode removeNthFromEnd3(ListNode head, int n) {
        ListNode dummyHead = new ListNode(0, head);
        Deque<ListNode> stack = new ArrayDeque<>();
        stack.push(dummyHead);
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        while (n > 0) {
            stack.pop();
            n--;
        }
        ListNode pre = stack.peek();
        pre.next = pre.next.next;
        return dummyHead.next;
    }


    /**
     * **********************************************
     * 练习题-进阶难度
     ************************************************/
    /**
     * LeetCode-148
     * 排序链表
     *      1. 快排
     */
//    public ListNode sortList(ListNode head) {
//
//    }
}
