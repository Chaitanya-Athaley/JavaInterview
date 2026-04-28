package com.code.basic.array;

import java.util.Arrays;

public class PairSumFor3Numbers {

	public static void main(String[] args) {
		int[] arr = {10, 5, 3, 2, 6, 1};
		int target = 13;
		// output = [10,2,1] , [5,2,6]
//		bruteforce(arr, target);
		findTriplets(arr, target);

	}

	private static void bruteforce(int[] arr, int target) {
		for (int i = 0; i < arr.length - 2 ; i++) {
			for (int j = i + 1; j < arr.length-1; j++) {
				for (int k = j + 1; k < arr.length; k++) {
					if(arr[i]+arr[j]+arr[k] == target) {
						System.out.println(arr[i]+", "+arr[j]+", "+arr[k]);
					}
				}
			}
		}
	}

	public static void findTriplets(int[] arr, int target) {
		if (arr == null || arr.length < 3) {
			return;
		}

		// Step 1: Sort the array
		Arrays.sort(arr);

		// Step 2: Fix one element and find pairs using two pointers
		for (int i = 0; i < arr.length - 2; i++) {
			int complement = target - arr[i];

			// Use two pointers to find pairs that sum to complement
			int left = i + 1;
			int right = arr.length - 1;

			while (left < right) {
				int sum = arr[left] + arr[right];

				if (sum == complement) {
					System.out.println(arr[i] + ", " + arr[left] + ", " + arr[right]);
					left++;
					right--;
				} else if (sum < complement) {
					left++;
				} else {
					right--;
				}
			}
		}
	}

}
