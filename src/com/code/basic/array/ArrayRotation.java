package com.code.basic.array;

public class ArrayRotation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Question 3: Array Rotation (Left Rotate by K Positions)
//		Given an integer array, rotate the array to the left by K positions.
//		Example:Input:  arr = [1, 2, 3, 4, 5], K = 2  
//		Output: [3, 4, 5, 1, 2]
		
		int[] arr = {1, 2, 3, 4, 5};
		int k = 2;
		
		//k = k%arr.length;
		
		for (int i = 0; i < k; i++) {
			rotate(arr);
		}
		
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}

	}

	private static void rotate(int[] arr) {
		int first = arr[0];
		for (int j = 0; j < arr.length -1 ; j++) {
			arr[j] = arr[j+1];
		}
		arr[arr.length -1]  = first;
	}

}
