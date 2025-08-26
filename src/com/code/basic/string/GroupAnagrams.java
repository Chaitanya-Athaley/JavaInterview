package com.code.basic.string;

import java.util.*;

public class GroupAnagrams {
    public static List<List<String>> group(String[] strs) {
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
    
    public static void main(String[] args) {
        String[] input = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Input array: " + Arrays.toString(input));
        List<List<String>> result = group(input);
        System.out.println("Grouped anagrams:");
        for (List<String> group : result) {
            System.out.println(group);
        }
        
        // Test edge cases
        System.out.println("\nTesting empty array:");
        System.out.println(group(new String[]{}));
        
        System.out.println("\nTesting null input:");
        System.out.println(group(null));
    }
}
