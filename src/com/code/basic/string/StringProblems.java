package com.code.basic.string;

import java.util.*;

public class StringProblems {
    // Problem 1: Find all anagrams in a string
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) return result;
        
        int[] pCount = new int[26];
        int[] window = new int[26];
        
        // Count characters in pattern
        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }
        
        // Initialize window
        for (int i = 0; i < p.length(); i++) {
            window[s.charAt(i) - 'a']++;
        }
        
        // Check first window
        if (Arrays.equals(pCount, window)) {
            result.add(0);
        }
        
        // Slide window
        for (int i = p.length(); i < s.length(); i++) {
            window[s.charAt(i - p.length()) - 'a']--;
            window[s.charAt(i) - 'a']++;
            
            if (Arrays.equals(pCount, window)) {
                result.add(i - p.length() + 1);
            }
        }
        
        return result;
    }
    
    // Problem 2: Longest Palindromic Substring
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 2) return s;
        
        int start = 0, maxLength = 1;
        
        for (int i = 0; i < s.length(); i++) {
            // Check odd length palindromes
            int len1 = expandAroundCenter(s, i, i);
            // Check even length palindromes
            int len2 = expandAroundCenter(s, i, i + 1);
            
            int len = Math.max(len1, len2);
            if (len > maxLength) {
                start = i - (len - 1) / 2;
                maxLength = len;
            }
        }
        
        return s.substring(start, start + maxLength);
    }
    
    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    
    public static void main(String[] args) {
        // Test anagram finding
        String s1 = "cbaebabacd";
        String p1 = "abc";
        System.out.println("Finding anagrams of '" + p1 + "' in '" + s1 + "':");
        System.out.println("Start indices: " + findAnagrams(s1, p1));
        
        // Test palindrome finding
        String[] testStrings = {
            "babad",
            "cbbd",
            "a",
            "ac",
            "racecar"
        };
        
        System.out.println("\nTesting Longest Palindromic Substring:");
        for (String s : testStrings) {
            System.out.println("Input: " + s);
            System.out.println("Longest palindrome: " + longestPalindrome(s));
        }
    }
}
