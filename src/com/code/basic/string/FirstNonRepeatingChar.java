package com.code.basic.string;

import java.util.HashMap;
import java.util.Map;

public class FirstNonRepeatingChar {
    public static int findFirst(String s) {
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
    
    public static void main(String[] args) {
        String[] tests = {"leetcode", "loveleetcode", "aabb"};
        for (String test : tests) {
            int index = findFirst(test);
            System.out.println(test + ": " + 
                (index == -1 ? "No non-repeating character" : 
                "First non-repeating character at index " + index));
        }
    }
}
