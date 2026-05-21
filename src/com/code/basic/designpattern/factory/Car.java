package com.code.basic.designpattern.factory;

/**
 * Concrete Product: Car
 * Implements the Vehicle interface for a car
 */
public class Car implements Vehicle {
    private boolean isRunning = false;

    @Override
    public void start() {
        isRunning = true;
        System.out.println("🚗 Car engine started with a smooth sound...");
    }

    @Override
    public void stop() {
        isRunning = false;
        System.out.println("🚗 Car engine stopped.");
    }

    @Override
    public void drive() {
        if (!isRunning) {
            System.out.println("❌ Car is not running. Start it first!");
            return;
        }
        System.out.println("🚗 Car is driving smoothly on the highway at optimal speed.");
    }

    @Override
    public String getType() {
        return "Car - 4 wheels, comfortable for passengers";
    }

    @Override
    public int getMaxSpeed() {
        return 200; // km/h
    }
}
