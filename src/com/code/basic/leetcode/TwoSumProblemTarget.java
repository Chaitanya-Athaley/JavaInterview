package com.code.basic.leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSumProblemTarget {

	public static void main(String[] args) {
		//Two Sum problem 
		//Given an array of integers (e.g., [2, 7, 11, 15]) and a target integer (e.g., 9),
		//you need to return the indices of the two numbers in the array that add up to the target.
		
		int[] nums = {2, 7, 11, 15};
		int target = 9;
		int[] result = null;
		for(int i=0; i<nums.length; i++) {
			for(int j=i+1; j<nums.length; j++) {
				if(nums[i] + nums[j] == target) {
					result = new int[] {i, j};
				}
			}
		}
		System.out.println(result[0]+"---"+ result[1]);
		
		//OR
		int[] result2 = null;
		Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for(int i=0; i<nums.length; i++) {
			int compliment = target - nums[i];
			if(hm.containsKey(compliment)) {
				result2 = new int[] {hm.get(compliment), i};
			}
			hm.put(nums[i], i);
		}
		System.out.println(result2[0]+"---"+ result2[1]);
	}

}
