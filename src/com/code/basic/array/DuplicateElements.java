package com.code.basic.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DuplicateElements {
	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 2, 5, 1};
		Map<Integer,Long> map = Arrays.stream(arr).boxed().collect(Collectors.groupingBy(n->n, Collectors.counting()));
		List<Integer> collect = map.entrySet().stream().filter(el->el.getValue()>1).map(Map.Entry::getKey).collect(Collectors.toList());
		System.out.println(collect);
		
		//core java logic
		Set<Integer> store = new HashSet<Integer>();
		for (int i = 0; i < arr.length; i++) {
			if(!store.add(arr[i])) {
				System.out.println(arr[i]);
			}
		}
		
	}
}
