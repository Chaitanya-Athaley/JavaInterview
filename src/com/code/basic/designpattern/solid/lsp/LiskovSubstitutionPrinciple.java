package com.code.basic.designpattern.solid.lsp;

/**
 * Liskov Substitution Principle (LSP)
 * Derived classes must be substitutable for their base classes.
 * A subclass should not violate the contract of the parent class.
 */

// BAD EXAMPLE - Violates LSP
class BirdBad {
    public void fly() {
        System.out.println("Bird is flying");
    }
}

class SparrowBad extends BirdBad {
    @Override
    public void fly() {
        System.out.println("Sparrow is flying");
    }
}

class PenguinBad extends BirdBad {
    @Override
    public void fly() {
        // Penguin cannot fly, but we're forced to implement it
        throw new UnsupportedOperationException("Penguins cannot fly!");
    }
}

// GOOD EXAMPLE - Follows LSP
interface Animal {
    void move();
}

interface Flyable {
    void fly();
}

class Sparrow implements Animal, Flyable {
    @Override
    public void move() {
        System.out.println("Sparrow is moving");
    }

    @Override
    public void fly() {
        System.out.println("Sparrow is flying");
    }
}

class Penguin implements Animal {
    @Override
    public void move() {
        System.out.println("Penguin is moving/waddling");
    }

    public void swim() {
        System.out.println("Penguin is swimming");
    }
}

// Demo
public class LiskovSubstitutionPrinciple {
    public static void main(String[] args) {
        System.out.println("=== BAD Example (LSP Violation) ===");
        BirdBad[] birds = {new SparrowBad(), new PenguinBad()};
        for (BirdBad bird : birds) {
            try {
                bird.fly(); // This will crash for Penguin
            } catch (UnsupportedOperationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("\n=== GOOD Example (LSP Followed) ===");
        Animal sparrow = new Sparrow();
        sparrow.move();

        Animal penguin = new Penguin();
        penguin.move();

        // Use Flyable interface for birds that can fly
        Flyable flyableSparrow = new Sparrow();
        flyableSparrow.fly();

        System.out.println("\nBenefit: Subclasses respect the contract of their parent");
        System.out.println("No unexpected exceptions or violations of expected behavior");
    }
}
