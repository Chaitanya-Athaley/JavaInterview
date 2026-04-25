package com.code.basic.array;

import java.util.Arrays;

public class ReverseArrayInPlace {

	public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
//        output = 5,4,3,2,1
        int left = 0;
        int right = arr.length - 1;
        while(left<=right) {
        	int temp = arr[left];
        	arr[left] = arr[right];
        	arr[right] = temp;
        	
        	left++;
        	right--;
        }
        Arrays.stream(arr).forEach(n -> System.out.println(n));
	}

}
