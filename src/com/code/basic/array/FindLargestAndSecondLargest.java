package com.code.basic.array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FindLargestAndSecondLargest {

	public static void main(String[] args) {
        int[] arr = {10, 5, 20, 8, 15};
        //Sort and limit 2 
        List<Integer> limit = Arrays.stream(arr).boxed()
        .sorted(Comparator.reverseOrder()).limit(2).collect(Collectors.toList());
        System.out.println(limit);
        
        // core java
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
			if(arr[i]>max) {
				secondMax = max;
				max = arr[i];
			}else if(arr[i]!=max && secondMax<arr[i]) {
				secondMax = arr[i];
			}
		}
        System.out.println(max+", "+secondMax);
	}

}
