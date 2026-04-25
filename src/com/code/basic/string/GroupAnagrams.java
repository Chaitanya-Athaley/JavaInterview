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
            if(!map.containsKey(key)) {
            	map.put(key, new ArrayList<String>());
            }
//            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(s);
        }
        
        return new ArrayList<>(map.values());
    }
    
    //if 2 strings are Anagrams
    public boolean areAnagrams(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        char[] chars1 = str1.toLowerCase().toCharArray();
        char[] chars2 = str2.toLowerCase().toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1, chars2);
    }
    
    // Without Sort the string char array
    public static boolean areAnagramWithoutSort(String s1, String s2) {
    	Map<Character, Integer> input1 = new HashMap<Character, Integer>();
		Map<Character, Integer> input2 = new HashMap<Character, Integer>();
    	if(s1.length() == s2.length()) {
    		for(int i=0; i<s1.length(); i++) {
    			input1.put(s1.charAt(i), input1.getOrDefault(s1.charAt(i), 0) + 1);
    			input2.put(s2.charAt(i), input2.getOrDefault(s2.charAt(i), 0) + 1);
    		}
        	return input1.equals(input2);
    	} else {
        	return false;
    	}
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
        
        boolean areAnagramWithoutSort = areAnagramWithoutSort("eat", "tea");
        System.out.println(areAnagramWithoutSort);
    }
}
