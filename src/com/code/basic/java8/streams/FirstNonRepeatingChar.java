package com.code.basic.java8.streams;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstNonRepeatingChar {

	public static void main(String[] args) {
		String input = "Programming";
		// first check frequency of all Character
		// find frequency = 1 and return
		Map<Character, Integer> frequencyStored = new HashMap<>();
		char[] charArray = input.toLowerCase().toCharArray();
		for(char c : charArray) {
			frequencyStored.put(c, frequencyStored.getOrDefault(c,0)+1);
		}
		char result = 0 ;
		for(char c : charArray) {
			if(frequencyStored.get(c)==1) {
				result = c;
				break;
			}
		}
		System.out.println(result);
		
		//------------------------------------------------------------//
		//----using java 8 ---//type name = new type();
		Map<Character, Long> collect = input.chars().mapToObj(n->(char)n).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Character character = collect.entrySet().stream().filter(e->e.getValue()==1).findFirst().map(Map.Entry::getKey).get();
		System.out.println(character);
	}

}
