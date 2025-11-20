package com.code.basic.string;

public class LongestCommonPrefix {
    public static String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0)
            return "";

        // Compare character by character
        for (int i = 0; i < strs[0].length(); i++) {

            char c = strs[0].charAt(i);

            for (int j = 1; j < strs.length; j++) {
                // If index exceeds or mismatch found → return prefix
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0]; // whole first string is the prefix
    }

    public static void main(String[] args) {
        String[] arr = {"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix(arr));
    }
}

