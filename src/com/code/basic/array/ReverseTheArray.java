package com.code.basic.array;

import java.util.Arrays;

public class ReverseTheArray {

	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5};
		int[] res = reverse(arr);
		System.out.println("return new reversed array "+Arrays.toString(res));
		reverseSwapOriginalArray(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void reverseSwapOriginalArray(int[] arr) {
		int left = 0;
		int right = arr.length -1;
		while(left<right) {
			int temp = arr[left];
			arr[left] = arr[right];
			arr[right] = temp;
			left++;
			right--;
		}
	}

	private static int[] reverse(int[] arr) {
		int[] tempArr = new int[arr.length];
		int count = 0;
		for (int i = arr.length - 1; i >= 0; i--) {
			tempArr[count++] = arr[i];
		}
		return tempArr;
	}
}
