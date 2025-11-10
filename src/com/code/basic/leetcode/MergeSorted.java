package com.code.basic.leetcode;

public class MergeSorted {

	public static void main(String[] args) {
		int[] a = {1, 3, 5, 7};
        int[] b = {2, 4, 6, 8};
        int[] mergeSorted = mergeSorted(a, b);
        for (int i = 0; i < mergeSorted.length; i++) {
			System.out.println(mergeSorted[i]);
		}
	}

	public static int[] mergeSorted(int[] a, int[] b) {
		int[] result = new int[a.length + b.length];
		int i = 0, j = 0, k = 0;

		while (i < a.length && j < b.length) {
			if (a[i] <= b[j]) {
				result[k++] = a[i++];
			} else {
				result[k++] = b[j++];
			}
		}

		// Copy remaining elements
		while (i < a.length) result[k++] = a[i++];
		while (j < b.length) result[k++] = b[j++];

		return result;
	}
}
