package com.code.basic.math;

public class FindGCD {
    public static void main(String[] args) {

        int a = 21;
        int b = 49;

        // Loop until both numbers are equal
        while (a != b) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }

        System.out.println("GCD is: " + a);  // or b, both are equal now
    }
}

