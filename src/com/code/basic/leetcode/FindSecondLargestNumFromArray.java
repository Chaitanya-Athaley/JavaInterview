package com.code.basic.leetcode;

import java.util.Arrays;

public class FindSecondLargestNumFromArray {

	public static void main(String[] args) {
		int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
		int secondLargest = findSecondLargest(arr);
		//OR
		int simple = simple(arr);
		System.out.println(secondLargest);
		System.out.println(simple);

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
		Arrays.sort(arr);
		return arr[arr.length - 2];
	}
}
