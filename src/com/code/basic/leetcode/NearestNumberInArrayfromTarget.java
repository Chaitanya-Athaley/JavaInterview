package com.code.basic.leetcode;

public class NearestNumberInArrayfromTarget {

	public static void main(String[] args) {
		int arr[] = {3, 7, 9, 0, 8, 15, 23};
		int target = 14;

		// Assume the first element is the closest to the target
		int nearest = arr[0];  
		int smallestDifference = Math.abs(arr[0] - target);  // Store the smallest difference found

		// Loop through the rest of the array
		for (int i = 1; i < arr.length; i++) {
			int difference = Math.abs(arr[i] - target);  // Find the difference between current element and target
			// If this difference is smaller than current smallest difference...
			if (difference < smallestDifference) {
				smallestDifference = difference;  // ...update smallest difference
				nearest = arr[i];                 // ...and update nearest number
			}
		}

		// Print the result
		System.out.println("Nearest number to target is: " + nearest);
	}
}
