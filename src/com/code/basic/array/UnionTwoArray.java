package com.code.basic.array;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnionTwoArray {

	public static void main(String[] args) {
		int[] arr1 = {1,2,3,4};
		int[] arr2 = {3,4,5,6};
		List<Integer> res = union(arr1, arr2);
		System.out.println(res);
	}

	private static List<Integer> union(int[] arr1, int[] arr2) {
		Set<Integer> store = new HashSet<Integer>();
		for (int i = 0; i < arr1.length; i++) {
			store.add(arr1[i]);
		}
		for (int i = 0; i < arr2.length; i++) {
			store.add(arr2[i]);
		}
		return new ArrayList<Integer>(store);
	}
}
