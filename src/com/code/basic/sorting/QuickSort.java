package com.code.basic.sorting;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    private static Random random = new Random();
    
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSort(arr, 0, arr.length - 1);
    }
    
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Choose pivot using random selection to avoid worst case in sorted arrays
            int pivotIndex = partition(arr, low, high);
            
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }
    
    private static int partition(int[] arr, int low, int high) {
        // Choose random pivot to avoid worst case in sorted arrays
        int randomPivotIndex = low + random.nextInt(high - low + 1);
        swap(arr, randomPivotIndex, high);
        
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    // Helper method to check if array is sorted
    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        // Test with random array
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array: " + Arrays.toString(arr));
        quickSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.println("Is sorted: " + isSorted(arr));
        
        // Test with already sorted array
        int[] sortedArr = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("\nSorted array input: " + Arrays.toString(sortedArr));
        quickSort(sortedArr);
        System.out.println("After quicksort: " + Arrays.toString(sortedArr));
        System.out.println("Is sorted: " + isSorted(sortedArr));
        
        // Test with reverse sorted array
        int[] reverseArr = {7, 6, 5, 4, 3, 2, 1};
        System.out.println("\nReverse sorted array input: " + Arrays.toString(reverseArr));
        quickSort(reverseArr);
        System.out.println("After quicksort: " + Arrays.toString(reverseArr));
        System.out.println("Is sorted: " + isSorted(reverseArr));
        
        // Test with duplicates
        int[] duplicateArr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        System.out.println("\nArray with duplicates: " + Arrays.toString(duplicateArr));
        quickSort(duplicateArr);
        System.out.println("After quicksort: " + Arrays.toString(duplicateArr));
        System.out.println("Is sorted: " + isSorted(duplicateArr));
    }
}
