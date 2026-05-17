package com.code.basic.array;

import java.util.Arrays;

public class RemoveDuplicates {

	public static void main(String[] args) {
		// sorted array
        int[] arr = {1,1,2,2,3};
        int unique = 0;
        for (int i = 1; i < arr.length; i++) {
			if(arr[i] != arr[unique]) {
				unique++;
				arr[unique] = arr[i];
			}
		}
        System.out.println(unique+1);
        System.out.println(Arrays.toString(arr));
	}
}
