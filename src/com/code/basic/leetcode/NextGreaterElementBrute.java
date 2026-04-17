package com.code.basic.leetcode;

public class NextGreaterElementBrute {

    public static void main(String[] args) {
        int[] nums = {4, 5, 2, 25};
        int[] result = nextGreaterElement(nums);
        for (int num : result) {
            System.out.print(num + " ");
        }

    }

    private static int[] nextGreaterElement(int[] nums) {
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = -1; // Initialize with -1, assuming no greater element is found, default value is -1
            for(int j = i + 1; j<nums.length; j++){
                if(nums[j]>nums[i]){
                    result[i] = nums[j]; // Update the result with the next greater element
                    break; // Break the inner loop once the next greater element is found
                }
            }
        }
        return result;
    }
    
}
