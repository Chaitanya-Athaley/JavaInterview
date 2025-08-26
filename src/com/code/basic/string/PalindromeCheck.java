package com.code.basic.string;

public class PalindromeCheck {
    // Method 1: Two-pointer approach
    public static boolean isPalindrome(String str) {
        if (str == null) return false;
        if (str.length() <= 1) return true;
        
        str = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        int left = 0;
        int right = str.length() - 1;
        
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    // Method 2: Using StringBuilder reverse
    public static boolean isPalindromeUsingStringBuilder(String str) {
        if (str == null) return false;
        if (str.length() <= 1) return true;
        
        str = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
    
    // Method 3: Using charAt(lastIndex) approach
    public static boolean isPalindromeUsingCharAt(String str) {
        if (str == null) return false;
        if (str.length() <= 1) return true;
        
        str = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        int length = str.length();
        
        for (int i = 0; i < length/2; i++) {
            if (str.charAt(i) != str.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        // Test basic palindrome check
        System.out.println("Testing Basic Palindrome Check:");
        String[] basicTests = {
            "A man, a plan, a canal: Panama",
            "race a car",
            "Was it a car or a cat I saw?",
            "hello",
            "12321",
            ""
        };
        
        for (String test : basicTests) {
            System.out.println("\"" + test + "\" is palindrome: " + isPalindrome(test));
            System.out.println("Using StringBuilder: " + isPalindromeUsingStringBuilder(test));
            System.out.println();
        }
    }
}
