package com.code.basic.leetcode;

public class ReverseVowelsSimple {
    public static void main(String[] args) {
        String input = "hello";
        System.out.println(reverseVowels(input));
    }

    public static String reverseVowels(String s) {

        // Step 1: Collect all vowels in a list (in reverse order)
        String vowels = "aeiouAEIOU";
        StringBuilder collected = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                collected.append(c); // store vowel
            }
        }

        collected.reverse(); // reverse vowels

        // Step 2: Replace vowels in original string with reversed vowels
        StringBuilder result = new StringBuilder();
        int index = 0;

        for (char c : s.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                result.append(collected.charAt(index)); 
                index++;
            } else {
                result.append(c); 
            }
        }

        return result.toString();
    }
}

