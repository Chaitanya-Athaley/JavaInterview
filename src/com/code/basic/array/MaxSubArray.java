package com.code.basic.array;

import java.util.Arrays;

public class MaxSubArray {

    /**
     * Returns the contiguous subarray with maximum sum.
     * For nums = [-2,1,-3,4,-1,2,1,-5,4] → [4, -1, 2, 1]
     */
    public int[] maxSubArraySub(int[] nums) {
        int maxSum = nums[0];
        int currentSum = nums[0];
        int start = 0, end = 0;
        int tempStart = 0;

        for (int i = 1; i < nums.length; i++) {
            // Decide: start fresh at i, or extend previous subarray
            if (currentSum < 0) {
                currentSum = nums[i];
                tempStart = i;
            } else {
                currentSum += nums[i];
            }

            // If current subarray sum is better, update result range
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }

        // Extract the subarray [start, end] inclusive
        return Arrays.copyOfRange(nums, start, end + 1);
    }

    // Example usage
    public static void main(String[] args) {
        MaxSubArray sol = new MaxSubArray();
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] result = sol.maxSubArraySub(nums);
        System.out.println(Arrays.toString(result)); // Output: [4, -1, 2, 1]
        System.out.println("----------------------------");
        int[] maxSubArrayBruteForce = sol.maxSubArrayBruteForce(nums);
        System.out.println(Arrays.toString(maxSubArrayBruteForce));
        System.out.println("----------------------------");
        int maxSubArrayBruteForceSum = sol.maxSubArrayBruteForceSum(nums);
        System.out.println(maxSubArrayBruteForceSum);
    }
    
    public int[] maxSubArrayBruteForce(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int bestStart = 0, bestEnd = 0;

        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];

                if (currentSum > maxSum) {
                    maxSum = currentSum;
                    bestStart = i;
                    bestEnd = j;
                }
            }
        }

        return Arrays.copyOfRange(nums, bestStart, bestEnd + 1);
    }
    
    public int maxSubArrayBruteForceSum(int[] nums) {
    	int maxSum = Integer.MIN_VALUE;
    	for (int i = 0; i < nums.length; i++) {
			int currentSum = 0;
			for (int j = i; j < nums.length; j++) {
				currentSum = currentSum + nums[j];
				maxSum = Math.max(maxSum, currentSum);
			}
		}
		return maxSum;
    	
    }

}