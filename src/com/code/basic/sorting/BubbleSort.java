package com.code.basic.sorting;

import java.util.Arrays;

public class BubbleSort {

    /**
     * Sorts an array of integers using the bubble sort algorithm.
     *
     * @param arr the array to be sorted
     */
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        BubbleSort sorter = new BubbleSort();
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array: " + Arrays.toString(arr));
        sorter.sort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
