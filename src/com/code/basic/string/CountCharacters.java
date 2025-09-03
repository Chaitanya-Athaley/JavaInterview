package com.code.basic.string;

import java.util.HashMap;
import java.util.Map;

public class CountCharacters {

	public static void main(String[] args) {
		Map<Character, Integer> countCharacters = countCharacters("aavvvrrrffffdddddddddu");
		System.out.println("countCharacters "+countCharacters);
	}
	//Count Character Occurrences
	public static Map<Character, Integer> countCharacters(String str) {
	    Map<Character, Integer> charCount = new HashMap<>();
	    for (char c : str.toCharArray()) {
	        charCount.put(c, charCount.getOrDefault(c, 0) + 1);
	    }
	    return charCount;
	}

}
