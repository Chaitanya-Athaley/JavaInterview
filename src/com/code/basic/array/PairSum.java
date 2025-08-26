package com.code.basic.array;

import java.util.HashSet;
import java.util.Set;

public class PairSum {
    public static void findPairs(int[] arr, int targetSum) {
        if (arr == null || arr.length < 2) {
            System.out.println("Array too small");
            return;
        }

        Set<Integer> seen = new HashSet<>();
        
        for (int num : arr) {
            int complement = targetSum - num;
            
            if (seen.contains(complement)) {
                System.out.printf("Pair found: (%d, %d)%n", complement, num);
            }
            
            seen.add(num);
        }
    }
    
    public static void main(String[] args) {
        int[] numbers = {2, 7, 11, 15, 3, 6, 8, 9, 1};
        int target = 10;
        
        System.out.println("Finding pairs with sum: " + target);
        findPairs(numbers, target);
        
        // Test edge cases
        System.out.println("\nTesting empty array:");
        findPairs(new int[]{}, 5);
        
        System.out.println("\nTesting single element array:");
        findPairs(new int[]{1}, 5);
    }
}
