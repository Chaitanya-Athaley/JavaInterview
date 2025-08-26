package com.code.basic.math;

import java.util.Scanner;

/**
 * A class to calculate factorial of a number.
 * Factorial of n (n!) is the product of all positive integers less than or equal to n.
 */
public class Factorial {
    
    /**
     * Calculates the factorial of a given number
     * @param n the number to calculate factorial for
     * @return the factorial of the number
     * @throws IllegalArgumentException if the number is negative
     */
    public static long calculateFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n > 20) {
            throw new IllegalArgumentException("Number too large, might cause overflow. Maximum supported number is 20");
        }
        
        long factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter a number to calculate its factorial (0-20): ");
            int number = scanner.nextInt();
            
            long result = calculateFactorial(number);
            System.out.printf("Factorial of %d is: %d%n", number, result);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Please enter a valid integer");
        } finally {
            scanner.close();
        }
    }
}
