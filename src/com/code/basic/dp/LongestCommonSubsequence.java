package com.code.basic.dp;

public class LongestCommonSubsequence {
    public static String findLCS(String text1, String text2) {
        if (text1 == null || text2 == null) return "";
        
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Reconstruct the LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                lcs.insert(0, text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        
        return lcs.toString();
    }
    
    public static void main(String[] args) {
        String str1 = "ABCDGH";
        String str2 = "AEDFHR";
        
        System.out.println("String 1: " + str1);
        System.out.println("String 2: " + str2);
        System.out.println("Longest Common Subsequence: " + findLCS(str1, str2));
        
        // Test with more examples
        str1 = "AGGTAB";
        str2 = "GXTXAYB";
        System.out.println("\nString 1: " + str1);
        System.out.println("String 2: " + str2);
        System.out.println("Longest Common Subsequence: " + findLCS(str1, str2));
        
        // Test edge cases
        System.out.println("\nTesting edge cases:");
        System.out.println("Empty strings: " + findLCS("", ""));
        System.out.println("One empty string: " + findLCS("ABC", ""));
        System.out.println("No common subsequence: " + findLCS("ABC", "DEF"));
    }
}
