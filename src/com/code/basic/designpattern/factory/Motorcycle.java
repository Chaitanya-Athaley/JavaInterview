package com.code.basic.designpattern.factory;

/**
 * Concrete Product: Motorcycle
 * Implements the Vehicle interface for a motorcycle
 */
public class Motorcycle implements Vehicle {
    private boolean isRunning = false;

    @Override
    public void start() {
        isRunning = true;
        System.out.println("🏍️ Motorcycle engine started with a high-pitched sound...");
    }

    @Override
    public void stop() {
        isRunning = false;
        System.out.println("🏍️ Motorcycle engine stopped.");
    }

    @Override
    public void drive() {
        if (!isRunning) {
            System.out.println("❌ Motorcycle is not running. Start it first!");
            return;
        }
        System.out.println("🏍️ Motorcycle is racing fast with agile maneuverability.");
    }

    @Override
    public String getType() {
        return "Motorcycle - 2 wheels, lightweight and agile";
    }

    @Override
    public int getMaxSpeed() {
        return 250; // km/h
    }
}
