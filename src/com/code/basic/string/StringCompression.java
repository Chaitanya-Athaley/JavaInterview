package com.code.basic.string;

public class StringCompression {
    public static String compress(String s) {
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
        String[] tests = {
            "aabcccccaaa",  // Should become "a2b1c5a3"
            "abcdef",       // Should stay "abcdef"
            "aabb",         // Should become "a2b2"
            "aaa",          // Should become "a3"
            "",            // Should stay ""
            "a"            // Should stay "a"
        };
        
        for (String test : tests) {
            System.out.println("Original string: \"" + test + "\"");
            System.out.println("Compressed string: \"" + compress(test) + "\"");
            System.out.println();
        }
    }
}
