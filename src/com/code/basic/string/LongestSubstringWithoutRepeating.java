package com.code.basic.string;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeating {
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
    
    public static void main(String[] args) {
        String[] tests = {
            "abcabcbb",    // Expected: 3 ("abc")
            "bbbbb",       // Expected: 1 ("b")
            "pwwkew",      // Expected: 3 ("wke")
            "",           // Expected: 0
            "a",          // Expected: 1
            "aab",        // Expected: 2 ("ab")
            "dvdf"        // Expected: 3 ("vdf")
        };
        
        for (String test : tests) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("Length of longest substring: " + 
                              lengthOfLongestSubstring(test));
            System.out.println();
        }
    }
}
