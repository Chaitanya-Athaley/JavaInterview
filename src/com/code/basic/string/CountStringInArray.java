package com.code.basic.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountStringInArray {

	public static void main(String[] args) {
		List<String> list = Arrays.asList("one", "two", "three", "one", "three", "one", "four");
		// using java 8 
		Map<String, Long> res = list.stream().collect(Collectors.groupingBy(n->n, Collectors.counting()));
//		System.out.println(res);
		
		// wihout java 8
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
//			if(map.containsKey(list.get(i))) {
//				map.put(list.get(i), map.getOrDefault(list.get(i), 0)+1);
//			} else {
//				map.put(list.get(i), 1);
//			}
			
			map.put(list.get(i), map.getOrDefault(list.get(i), 0)+1);
			
		}
		System.out.println(map);

	}

}
