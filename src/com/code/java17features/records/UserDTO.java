package com.code.java17features.records;

public record UserDTO(String name, int age, String email) {

   public UserDTO {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
