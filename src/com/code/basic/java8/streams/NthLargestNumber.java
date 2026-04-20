package com.code.basic.java8.streams;

import java.util.Arrays;
import java.util.Comparator;

public class NthLargestNumber {

	public static void main(String[] args) {
		int[] arr = {1,4,3,2,9,5,99,33,49,50};
		int n = 3;
//		int max = Arrays.stream(arr).max().getAsInt();
//		System.out.println(max);
		
		Integer collect = Arrays.stream(arr)
				.boxed()
				.sorted(Comparator.reverseOrder())
				.skip(n - 1)
				.findFirst().get();
		System.out.println(collect);
		
	}

}
