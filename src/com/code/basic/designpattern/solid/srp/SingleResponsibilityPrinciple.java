package com.code.basic.designpattern.solid.srp;

/**
 * Single Responsibility Principle (SRP)
 * A class should have only one reason to change.
 * Each class should have a single, well-defined responsibility.
 */

// BAD EXAMPLE - Violates SRP
class UserBad {
    private String name;
    private String email;

    public UserBad(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Responsibility 1: Managing user data
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Responsibility 2: Saving to database - SHOULD NOT BE HERE
    public void saveToDatabase() {
        System.out.println("Saving user " + name + " to database");
    }

    // Responsibility 3: Sending email - SHOULD NOT BE HERE
    public void sendEmail() {
        System.out.println("Sending email to " + email);
    }
}

// GOOD EXAMPLE - Follows SRP
class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

// Separate class for database operations
class UserRepository {
    public void save(User user) {
        System.out.println("Saving user " + user.getName() + " to database");
    }
}

// Separate class for email operations
class EmailService {
    public void sendEmail(User user) {
        System.out.println("Sending email to " + user.getEmail());
    }
}

// Demo
public class SingleResponsibilityPrinciple {
    public static void main(String[] args) {
        User user = new User("Chaitanya", "chaitanya@example.com");

        // Each class has only one responsibility
        UserRepository repository = new UserRepository();
        repository.save(user);

        EmailService emailService = new EmailService();
        emailService.sendEmail(user);

        System.out.println("\nBenefit: Each class has a single reason to change");
        System.out.println("If database logic changes, only UserRepository is affected");
        System.out.println("If email logic changes, only EmailService is affected");
    }
}
