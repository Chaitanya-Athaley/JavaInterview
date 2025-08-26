package com.code.basic.string;

import java.util.*;

public class StringProblems {
    // Problem 1: Valid Parentheses
    public static boolean isValidParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == '}' && top != '{') return false;
                if (c == ']' && top != '[') return false;
            }
        }
        return stack.isEmpty();
    }
    
    // Problem 2: First Non-Repeating Character
    public static int firstNonRepeatingChar(String s) {
        Map<Character, Integer> frequency = new HashMap<>();
        
        // Count frequency of each character
        for (char c : s.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        
        // Find first character with frequency 1
        for (int i = 0; i < s.length(); i++) {
            if (frequency.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }
    
    // Problem 3: Group Anagrams
    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        
        Map<String, List<String>> map = new HashMap<>();
        
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(s);
        }
        
        return new ArrayList<>(map.values());
    }
    
    // Problem 4: Longest Substring Without Repeating Characters
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLength = 0;
        int start = 0;
        
        for (int end = 0; end < s.length(); end++) {
            char currentChar = s.charAt(end);
            if (lastSeen.containsKey(currentChar)) {
                start = Math.max(start, lastSeen.get(currentChar) + 1);
            }
            lastSeen.put(currentChar, end);
            maxLength = Math.max(maxLength, end - start + 1);
        }
        
        return maxLength;
    }
    
    // Problem 5: String Compression
    public static String compressString(String s) {
        if (s == null || s.length() <= 1) return s;
        
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        char current = s.charAt(0);
        
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == current) {
                count++;
            } else {
                compressed.append(current).append(count);
                current = s.charAt(i);
                count = 1;
            }
        }
        compressed.append(current).append(count);
        
        return compressed.length() < s.length() ? compressed.toString() : s;
    }
    
    public static void main(String[] args) {
        // Test Valid Parentheses
        System.out.println("Testing Valid Parentheses:");
        String[] parenthesesTests = {"()", "()[]{}", "(]", "([)]", "{[]}"};
        for (String test : parenthesesTests) {
            System.out.println(test + " is valid: " + isValidParentheses(test));
        }
        
        // Test First Non-Repeating Character
        System.out.println("\nTesting First Non-Repeating Character:");
        String[] nonRepeatingTests = {"leetcode", "loveleetcode", "aabb"};
        for (String test : nonRepeatingTests) {
            int index = firstNonRepeatingChar(test);
            System.out.println(test + ": " + (index == -1 ? "No non-repeating character" : 
                             "First non-repeating character at index " + index));
        }
        
        // Test Group Anagrams
        System.out.println("\nTesting Group Anagrams:");
        String[] anagramsInput = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> groupedAnagrams = groupAnagrams(anagramsInput);
        System.out.println("Grouped anagrams: " + groupedAnagrams);
        
        // Test Longest Substring Without Repeating Characters
        System.out.println("\nTesting Longest Substring Without Repeating Characters:");
        String[] substringTests = {"abcabcbb", "bbbbb", "pwwkew"};
        for (String test : substringTests) {
            System.out.println(test + ": Length = " + lengthOfLongestSubstring(test));
        }
        
        // Test String Compression
        System.out.println("\nTesting String Compression:");
        String[] compressionTests = {"aabcccccaaa", "abcdef", "aabb", "aaa"};
        for (String test : compressionTests) {
            System.out.println(test + " -> " + compressString(test));
        }
    }
}
