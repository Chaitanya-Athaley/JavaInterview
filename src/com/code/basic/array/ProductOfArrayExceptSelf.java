package com.code.basic.array;

import java.util.Arrays;

public class ProductOfArrayExceptSelf {

    /**
     * Solves the product of array except self problem.
     * 
     *
     * @param nums the input integer array
     * @return an array where each element is the product of all other elements
     */
    public static int[] productExceptSelf(int[] nums) {
        // Input validation
        if (nums == null || nums.length == 0) {
            return new int[0]; // Return empty array for null or empty input
        }
        
        int n = nums.length;
        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
				if(i == j) {
					continue;
				}
				product = product * nums[j];
			}
            answer[i] = product;
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Product of Array Except Self");
        System.out.println("========================================");
        
        // Test Case 1: Basic positive numbers
        int[] nums1 = {1, 2, 3, 4};
        System.out.println("\n[Test Case 1: Basic Positive Numbers]");
        System.out.println("Input:    " + Arrays.toString(nums1));
        System.out.println("Output:   " + Arrays.toString(productExceptSelf(nums1)));
        System.out.println("Expected: [24, 12, 8, 6]");
        
        // Test Case 2: Array with zeros and negative numbers
        int[] nums2 = {-1, 1, 0, -3, 3};
        System.out.println("\n\n[Test Case 2: Negative Numbers and Zero]");
        System.out.println("Input:    " + Arrays.toString(nums2));
        System.out.println("Output:   " + Arrays.toString(productExceptSelf(nums2)));
        System.out.println("Expected: [0, 0, 9, 0, 0]");
        
        // Test Case 3: Two elements
        int[] nums3 = {2, 3};
        System.out.println("\n[Test Case 3: Two Elements]");
        System.out.println("Input:    " + Arrays.toString(nums3));
        System.out.println("Output:   " + Arrays.toString(productExceptSelf(nums3)));
        System.out.println("Expected: [3, 2]");
        
        // Test Case 4: All same numbers
        int[] nums4 = {5, 5, 5, 5};
        System.out.println("\n[Test Case 4: All Same Numbers]");
        System.out.println("Input:    " + Arrays.toString(nums4));
        System.out.println("Output:   " + Arrays.toString(productExceptSelf(nums4)));
        System.out.println("Expected: [125, 125, 125, 125]");
        
        System.out.println("\n========================================");
    }
}
