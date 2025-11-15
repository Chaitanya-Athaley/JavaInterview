package com.code.basic.designpattern.solid.isp;

/**
 * Interface Segregation Principle (ISP)
 * Clients should not be forced to depend on interfaces they don't use.
 * Create specific interfaces rather than general ones.
 */

// BAD EXAMPLE - Violates ISP
interface WorkerBad {
    void work();
    void eat();
    void sleep();
}

class RobotBad implements WorkerBad {
    @Override
    public void work() {
        System.out.println("Robot is working");
    }

    @Override
    public void eat() {
        // Robot doesn't eat, but forced to implement
        System.out.println("Robot doesn't eat - but forced to implement!");
    }

    @Override
    public void sleep() {
        // Robot doesn't sleep, but forced to implement
        System.out.println("Robot doesn't sleep - but forced to implement!");
    }
}

class HumanBad implements WorkerBad {
    @Override
    public void work() {
        System.out.println("Human is working");
    }

    @Override
    public void eat() {
        System.out.println("Human is eating");
    }

    @Override
    public void sleep() {
        System.out.println("Human is sleeping");
    }
}

// GOOD EXAMPLE - Follows ISP
interface Worker {
    void work();
}

interface Eater {
    void eat();
}

interface Sleeper {
    void sleep();
}

class Robot implements Worker {
    @Override
    public void work() {
        System.out.println("Robot is working");
    }
}

class Human implements Worker, Eater, Sleeper {
    @Override
    public void work() {
        System.out.println("Human is working");
    }

    @Override
    public void eat() {
        System.out.println("Human is eating");
    }

    @Override
    public void sleep() {
        System.out.println("Human is sleeping");
    }
}

// Demo
public class InterfaceSegregationPrinciple {
    public static void main(String[] args) {
        System.out.println("=== BAD Example (ISP Violation) ===");
        RobotBad robot = new RobotBad();
        robot.work();
        robot.eat(); // Forced to implement unnecessary method
        robot.sleep(); // Forced to implement unnecessary method

        System.out.println("\n=== GOOD Example (ISP Followed) ===");
        Worker robotWorker = new Robot();
        robotWorker.work();

        Human human = new Human();
        human.work();
        human.eat();
        human.sleep();

        System.out.println("\nBenefit: Classes only implement methods they actually use");
        System.out.println("No unnecessary method implementations");
    }
}
