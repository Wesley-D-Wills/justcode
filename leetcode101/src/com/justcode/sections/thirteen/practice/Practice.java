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
     * LeetCode-147
     * 对链表进行插入排序
     *      1. 链表插入排序： 时间复杂度O(n^2), 空间复杂度O(1)
     */
    // 1. 插入排序
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0, head);

        ListNode cur = head.next;
        ListNode lastSorted = head;
        while (cur != null) {
            if (lastSorted.val <= cur.val) {
                lastSorted = lastSorted.next;
                cur = lastSorted.next;
            } else {
                ListNode pre = dummy;
                while (pre.next.val <= cur.val) {
                    pre = pre.next;
                }
                lastSorted.next = cur.next;
                cur.next = pre.next;
                pre.next = cur;
                cur = lastSorted.next;
            }
        }

        return dummy.next;
    }
    // 1.1 插入排序
    public ListNode insertionSortList_1_1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0, head);
        ListNode cur = head.next;
        ListNode pre = head;
        while (cur != null) {
            if (pre.val <= cur.val) {
                pre = cur;
                cur = cur.next;
            } else {
                ListNode tmp = dummy;
                while (tmp.next.val <= cur.val) {
                    tmp = tmp.next;
                }
                pre.next = cur.next;
                cur.next = tmp.next;
                tmp.next = cur;
                cur = pre.next;
            }
        }
        return dummy.next;
    }

    // 冒泡排序
    public ListNode bubbleSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre;
        ListNode cur;
        boolean isSorted;
        while (true) {
            isSorted = true;
            pre = head;
            cur = head.next;
            while (cur != null) {
                if (pre.val > cur.val) {
                    swap(pre, cur);
                    isSorted = false;
                }
                pre = cur;
                cur = cur.next;
            }
            if (isSorted) {
                break;
            }
        }
        return head;
    }

    private void swap(ListNode pre, ListNode cur) {
        int tmp = pre.val;
        pre.val = cur.val;
        cur.val = tmp;
    }

    // 选择排序
    public ListNode selectSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode sorted = head;
        while (sorted != null) {
            ListNode cur = sorted;
            ListNode tmp = cur;
            while (cur != null) {
                if (tmp.val > cur.val) {
                    tmp = cur;
                }
                cur = cur.next;
            }
            swap(sorted, tmp);
            sorted = sorted.next;
        }
        return head;
    }

    /**
     * LeetCode-148
     * 排序链表
     *      1. 归并排序： 时间复杂度 O(nLogn), 空间复杂度O(n) 存在递归空间
     *      2. 快速排序，选择排序，冒泡排序 https://www.cnblogs.com/TenosDoIt/p/3666585.html
     */
    private ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode mid = slow.next;
        slow.next = null;
        ListNode list1 = sortList(head);
        ListNode list2 = sortList(mid);
        ListNode sorted = merge(list1, list2);
        return sorted;
    }
    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode pre = dummyHead;
        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                pre.next = head1;
                head1 = head1.next;
            } else {
                pre.next = head2;
                head2 = head2.next;
            }
            pre = pre.next;
        }
        if (head1 == null) {
            pre.next = head2;
        } else {
            pre.next = head1;
        }
        return dummyHead.next;
    }

    // 快速排序
    public ListNode quickSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        quickSortList(head, null);
        return head;
    }

    private void quickSortList(ListNode head, ListNode tail) {
        if (head == tail || head.next == tail) {
            return;
        }
        int val = head.val;
        ListNode cur = head;
        for (ListNode i = head.next; i != tail ; i = i.next) {
            if (i.val < val) {
                cur = cur.next;
                swap(i, cur);
            }
        }
        swap(head, cur);
        quickSortList(head, cur);
        quickSortList(cur.next, tail);
    }
}
