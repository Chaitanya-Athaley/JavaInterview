package com.code.basic.array;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MissingSmallestPositiveNumber {

    public static void main(String[] args) {
        int[] arr =  {1, 2, 3, 4, 6, 2, 5};
        System.out.println(findMissingSmallestPositive(arr));
    }

    public static int findMissingSmallestPositive(int[] arr) {
        // Return 0 if all numbers are non-positive
        if (Arrays.stream(arr).noneMatch(n -> n > 0)) {
            return 0;
        }
        
        // Create set with only positive numbers for O(1) lookup
        Set<Integer> arraySet = Arrays.stream(arr)
            .filter(n -> n > 0)
            .boxed()
            .collect(Collectors.toSet());
        
        // Find the smallest missing positive integer
        for (int i = 1; i <= arr.length; i++) {
            if (!arraySet.contains(i)) {
                return i;
            }
        }
        
        // If all numbers from 1 to arr.length are present, return arr.length + 1
        return arr.length + 1;
    }
}
