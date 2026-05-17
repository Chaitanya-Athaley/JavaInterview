package com.code.java17features.records;

public class UseOfUserDTORec {

    public static void main(String[] args) {
        UserDTO user1 = new UserDTO("Alice", 30, "t7C6w@example.com");
        // user1.setAge(31); // This will cause a compilation error since records are immutable
        user1 = new UserDTO("Jayesh", 29, "t7C6999w@example.com");
        System.out.println(user1); // toString() method is automatically generated for records
        System.out.println(user1.name()); // Accessor method for name
        System.out.println(user1.age());  // Accessor method for age
        System.out.println(user1.email()); // Accessor method for email

        // Testing validation in the compact constructor
        try {
            UserDTO invalidUser = new UserDTO("Bob", -5, "bobexample.com");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
