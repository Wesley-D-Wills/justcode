package com.justcode.sections.fourteen;

/** ***********************************************
 * 字典树 （前缀树）
 * ************************************************/
/**
 * LeetCode-208
 * 时间复杂度：初始化为 O(1)O(1)，其余操作为 O(|S|)O(∣S∣)，其中 |S|∣S∣ 是每次插入或查询的字符串的长度。
 * 空间复杂度：O(∣T∣⋅Σ)，其中∣T∣ 为所有插入字符串的长度之和，Σ 为字符集的大小，本题 Σ=26。
 *
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。
 * 这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 */
public class DictTree {
    private DictTree [] children;
    private boolean isEnd;
    /** Initialize your data structure here. */
    public DictTree() {
        children = new DictTree[26];
        isEnd = false;
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        DictTree dictTree = this;
        for (int i = 0; i < word.length(); i++) {
            int offset = word.charAt(i) - 'a';
            if (dictTree.children[offset] == null) {
                // 创建节点
                dictTree.children[offset] = new DictTree();
                dictTree = dictTree.children[offset];
            } else {
                dictTree = dictTree.children[offset];
            }
        }
        dictTree.isEnd = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        DictTree trie = this;
        for (int i = 0; i < word.length(); i++) {
            int offset = word.charAt(i) - 'a';
            if (trie.children[offset] == null) {
                return false;
            }
            trie = trie.children[offset];
        }
        return trie.isEnd;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        DictTree trie = this;
        for (int i = 0; i < prefix.length(); i++) {
            int offset = prefix.charAt(i) - 'a';
            if (trie.children[offset] == null) {
                return false;
            }
            trie = trie.children[offset];
        }
        return true;
    }
}
