package com.justcode.sections.twelve;

public class MyString {
    /** ***********************************************
     * 字符串的比较
     * ************************************************/
    /**
     * LeetCode-242
     * 有效的字母异位词
     *
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int [] flagArr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            flagArr[s.charAt(i) - 'a'] ++;
            flagArr[t.charAt(i) - 'a'] --;
        }
        for (int i = 0; i < 26; i++) {
            if (flagArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        MyString ms = new MyString();
        ms.isAnagram("anagram", "nagaram");
        System.out.println("weixu");
    }
}
