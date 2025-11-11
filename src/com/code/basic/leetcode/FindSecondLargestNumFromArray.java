package com.code.basic.leetcode;

import java.util.Arrays;
import java.util.Comparator;

public class FindSecondLargestNumFromArray {

	public static void main(String[] args) {
		int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 9};
		int secondLargest = findSecondLargest(arr);
		//OR
		int simple = simple(arr);
		System.out.println(secondLargest);
		System.out.println(simple);
		//java 8
		Integer integer = Arrays.stream(arr).distinct().boxed().sorted(Comparator.reverseOrder()).skip(1).findFirst().get();
		System.out.println(integer);
	}

	public static int findSecondLargest(int[] arr) {
		if (arr.length < 2) {
			throw new IllegalArgumentException("Array must have at least 2 elements");
		}

		int largest = Integer.MIN_VALUE;
		int secondLargest = Integer.MIN_VALUE;

		for (int num : arr) {
			if (num > largest) {
				secondLargest = largest;
				largest = num;
			} else if (num > secondLargest && num != largest) {
				secondLargest = num;
			}
		}

		if (secondLargest == Integer.MIN_VALUE) {
			throw new IllegalArgumentException("No second largest element found");
		}

		return secondLargest;
	}

	static int simple(int[] arr){
		// not work for duplicate elements
		Arrays.sort(arr);
		return arr[arr.length - 2];
	}
}
