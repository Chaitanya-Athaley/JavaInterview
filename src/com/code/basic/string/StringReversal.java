package com.code.basic.string;

public class StringReversal {
    public static String reverseString(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        // Method 1: Using StringBuilder
        StringBuilder reversed = new StringBuilder(str).reverse();
        return reversed.toString();
        
        // Method 2: Manual character by character (commented out)
        /*
        char[] characters = str.toCharArray();
        int left = 0;
        int right = str.length() - 1;
        
        while (left < right) {
            char temp = characters[left];
            characters[left] = characters[right];
            characters[right] = temp;
            left++;
            right--;
        }
        
        return new String(characters);
        */
        
//        String reversed = "";
//        for (int i = str.length() - 1; i >= 0; i--) {
//            reversed += str.charAt(i);
//        }
//        return reversed;
    }
    
    public static void main(String[] args) {
        String test = "Hello World";
        System.out.println("Original string: " + test);
        System.out.println("Reversed string: " + reverseString(test));
        
        // Test empty string
        System.out.println("\nTesting empty string:");
        System.out.println("Reversed: " + reverseString(""));
        
        // Test null
        System.out.println("\nTesting null:");
        System.out.println("Reversed: " + reverseString(null));
    }
}
