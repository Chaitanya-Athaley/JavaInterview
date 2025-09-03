package com.code.basic.sorting;

import java.util.Arrays;

public class MergeSort {

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        System.out.println("Starting Merge Sort on: " + Arrays.toString(arr));
        mergeSort(arr, 0, arr.length - 1, 0);
    }

    private void mergeSort(int[] arr, int left, int right, int level) {
        String indent = " ".repeat(level * 4);
        System.out.println(indent + "-> Diving into mergeSort(" + left + ", " + right + ")");
        if (left < right) {
            // Find the middle point to divide the array into two halves
            int mid = left + (right - left) / 2;

            // Recursively sort the first and second halves
            System.out.println(indent + "   Sorting left half...");
            mergeSort(arr, left, mid, level + 1);
            System.out.println(indent + "   Sorting right half...");
            mergeSort(arr, mid + 1, right, level + 1);

            // Merge the sorted halves
            System.out.println(indent + "   Merging halves...");
            merge(arr, left, mid, right, level);
        }
        System.out.println(indent + "<- Returning from mergeSort(" + left + ", " + right + "). Array state: " + Arrays.toString(Arrays.copyOfRange(arr, left, right + 1)));
    }

    private void merge(int[] arr, int left, int mid, int right, int level) {
        String indent = " ".repeat(level * 4);
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temp arrays
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[mid + 1 + j];
        }
        System.out.println(indent + "   Left sub-array: " + Arrays.toString(L));
        System.out.println(indent + "   Right sub-array: " + Arrays.toString(R));


        // Merge the temp arrays

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
        System.out.println(indent + "   Merged array segment: " + Arrays.toString(Arrays.copyOfRange(arr, left, right + 1)));
    }

    public static void main(String[] args) {
        MergeSort sorter = new MergeSort();
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("Original array: " + Arrays.toString(arr));
        System.out.println("------------------------------------");
        sorter.sort(arr);
        System.out.println("------------------------------------");
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
