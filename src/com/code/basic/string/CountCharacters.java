package com.code.basic.string;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountCharacters {

	public static void main(String[] args) {
		String input = "aavvvrrrffffdddddddddu";
		Map<Character, Long> collect = input.chars().mapToObj(n-> (char)n).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(collect);
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
