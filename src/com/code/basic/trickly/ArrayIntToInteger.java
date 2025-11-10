package com.code.basic.trickly;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayIntToInteger {

	public static void main(String[] args) {
		int[] arr =  {1,2,3,4,5};
		List<Integer> collect = Arrays.stream(arr)
				.boxed() // Convert int to Integer, then only collect will available
				.collect(Collectors.toList());
		System.out.println(collect);
		
		int[] array = Arrays.stream(arr)
				.boxed() // Convert int to Integer, then only collect will available
				.mapToInt(Integer::intValue) // Convert Integer to int  
				.toArray(); // convert to Array
		System.out.println(array);

		
		 int[] array2 = Arrays.stream(arr)
				.boxed() // Convert int to Integer, then only collect will available
				.filter(n-> n%2==0)
				.map(n-> n*n)
				.mapToInt(Integer::intValue)
				.toArray();
			System.out.println(array2);

		 
	}
}
