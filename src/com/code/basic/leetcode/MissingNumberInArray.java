package com.code.basic.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MissingNumberInArray {

	public static void main(String[] args) {
		int[] input = {1,2,3,5,10,11,28};
		Set<Integer> allNums = Arrays.stream(input).boxed().map(Integer::intValue).collect(Collectors.toSet());
		int max = Arrays.stream(input).max().getAsInt();
		int min = Arrays.stream(input).min().getAsInt();
		Set<Integer> set = new HashSet<>(allNums);
		for(int i=min; i<max; i++) {
			if(!set.contains(i)) {
				System.out.println(i);
			}
		}
	}
	
	void anotherWay(){
		// if array is sorted
		 int[] input = {1,2,3,5,10,11,28};
	        for (int i = 1; i < input.length; i++) {
	            for (int j = input[i-1]+1; j < input[i]; j++) {
	                System.out.println(j);
	            }
	        }
	}

}
