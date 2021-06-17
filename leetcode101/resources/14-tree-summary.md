# 树

## 二叉搜索树

二叉搜索树是一种特殊的二叉树，对于每个父节点，其左子树所有节点的值小于等于父节点，其右子树所有节点的值大于等于父节点

* 恢复二叉搜索树 [参考LeetCode 99题](https://leetcode-cn.com/problems/recover-binary-search-tree/)

  常用解法考虑二叉搜索树的性质，**二叉搜索树的中序遍历是一个排好序的数组**

  * **解法一：**递归实现二叉树的中序遍历，在中序遍历的过程中判断是否存在 **前后两个节点的值大小违背了二叉搜索树的概念**,时间复杂度O(n)， 空间复杂度O(n)

  ```java
  TreeNode pre = null; // 记录二叉树搜索过程中当前节点的前节点
  TreeNode left = null; // 记录交换的两个节点的左右节点
  TreeeNode right = null; 
  
  public void inorder(TreeNode root) {
      if (root == null) {
          return;
      }
      inorder(root.left);
      /***************************************/
      if (pre != null && root.val < pre.val) {	// 判断当前节点和前节点值得大小，并更新左右TreeNode节点
          if (left == null) {
              left = pre;
          }
          right = root;
      }
      pre = root;	// 更新前节点
      /***************************************/
      inorder(root.right);
  }
  
  // 得到左右节点，交换左右节点的val即可
  ```

  

  * **解法二：**非递归实现二叉树的中序遍历，时间复杂度O(n)， 空间复杂度O(n)

  ```java
  public void inorderNonRecu(TreeNode root) {
      TreeNode left = null;
      TreeNode right = null;
      TreeNode pre = null;
      Deque<TreeNode> stack = new ArrayDeque<>();
      while(!stack.isEmpty() || root != null) {
          while (root != null) {
              stack.offerLast(root);
              root = root.left;
          }
          root = stack.pollLast();
          /**************************************/
          // 与解法一相同
          /**************************************/
          root = root.right;
      }
      // 得到左右节点，交换左右节点的val即可
  }
  ```

  

  * **解法三：** Morris算法 [数据结构与算法之Morris算法](https://zhuanlan.zhihu.com/p/346535728)

  

* 

* 