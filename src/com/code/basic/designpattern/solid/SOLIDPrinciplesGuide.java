package com.code.basic.designpattern.solid;

/**
 * SOLID Design Principles - Quick Reference
 * 
 * SOLID is an acronym for five design principles that make software more maintainable,
 * flexible, and scalable.
 */

public class SOLIDPrinciplesGuide {
    /**
     * S - Single Responsibility Principle (SRP)
     * 
     * Definition: A class should have only one reason to change.
     * Each class should have a single, well-defined responsibility.
     * 
     * Key Benefits:
     * - Easy to understand and test
     * - Reduced code complexity
     * - Changes in one responsibility don't affect others
     * 
     * Example:
     * ✗ BAD: User class handling data, database, and email
     * ✓ GOOD: User (data), UserRepository (database), EmailService (email)
     * 
     * Location: src/com/code/basic/designpattern/solid/srp/SingleResponsibilityPrinciple.java
     */
    public static final String SRP = "Single Responsibility Principle";

    /**
     * O - Open-Closed Principle (OCP)
     * 
     * Definition: Software entities should be open for extension but closed for modification.
     * You should be able to add new features without changing existing code.
     * 
     * Key Benefits:
     * - Reduced risk of breaking existing code
     * - Easy to add new features
     * - Promotes use of interfaces and abstraction
     * 
     * Example:
     * ✗ BAD: Adding if-else for each customer type (modify existing method)
     * ✓ GOOD: Create strategy interfaces for each customer type (extend, don't modify)
     * 
     * Location: src/com/code/basic/designpattern/solid/ocp/OpenClosedPrinciple.java
     */
    public static final String OCP = "Open-Closed Principle";

    /**
     * L - Liskov Substitution Principle (LSP)
     * 
     * Definition: Derived classes must be substitutable for their base classes.
     * A subclass should not violate the contract of the parent class.
     * 
     * Key Benefits:
     * - Predictable behavior in polymorphism
     * - Prevents unexpected runtime errors
     * - Ensures inheritance is used correctly
     * 
     * Example:
     * ✗ BAD: Bird class with fly() method, Penguin extends Bird but can't fly
     * ✓ GOOD: Animal interface, Flyable interface (separate concerns)
     * 
     * Location: src/com/code/basic/designpattern/solid/lsp/LiskovSubstitutionPrinciple.java
     */
    public static final String LSP = "Liskov Substitution Principle";

    /**
     * I - Interface Segregation Principle (ISP)
     * 
     * Definition: Clients should not be forced to depend on interfaces they don't use.
     * Create specific interfaces rather than general ones.
     * 
     * Key Benefits:
     * - Interfaces are focused and specific
     * - Reduces unnecessary dependencies
     * - Easier to implement and maintain
     * 
     * Example:
     * ✗ BAD: Worker interface with work(), eat(), sleep() (Robot forced to implement all)
     * ✓ GOOD: Separate Worker, Eater, Sleeper interfaces
     * 
     * Location: src/com/code/basic/designpattern/solid/isp/InterfaceSegregationPrinciple.java
     */
    public static final String ISP = "Interface Segregation Principle";

    /**
     * D - Dependency Inversion Principle (DIP)
     * 
     * Definition: High-level modules should not depend on low-level modules.
     * Both should depend on abstractions. Depend on interfaces, not concrete implementations.
     * 
     * Key Benefits:
     * - Loose coupling between modules
     * - Easy to swap implementations
     * - Better for unit testing (can use mocks)
     * 
     * Example:
     * ✗ BAD: UserService depends directly on MySQLDatabase (tight coupling)
     * ✓ GOOD: UserService depends on Database interface (loose coupling)
     * 
     * Location: src/com/code/basic/designpattern/solid/dip/DependencyInversionPrinciple.java
     */
    public static final String DIP = "Dependency Inversion Principle";

    public static void main(String[] args) {
        System.out.println("SOLID Design Principles Guide");
        System.out.println("==============================\n");
        System.out.println("S - " + SRP);
        System.out.println("O - " + OCP);
        System.out.println("L - " + LSP);
        System.out.println("I - " + ISP);
        System.out.println("D - " + DIP);
        System.out.println("\nRun individual examples to see practical implementations!");
    }
}
