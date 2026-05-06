package com.code.basic.array;

import java.util.ArrayList;
import java.util.List;

public class TwoArrayIntersaction {

	public static void main(String[] args) {
		int[] arr1 = {1,2,3,4};
		int[] arr2 = {3,4,5,6};
		List<Integer> res = intersact(arr1, arr2);
		System.out.println(res);
	}

	private static List<Integer> intersact(int[] arr1, int[] arr2) {
		List<Integer> li = new ArrayList<Integer>();
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr2.length; j++) {
				if(arr1[i] == arr2[j] && !li.contains(arr1[i])) {
					li.add(arr1[i]);
				}
			}
		}
		return li;
	}
}
