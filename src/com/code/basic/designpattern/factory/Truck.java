package com.code.basic.designpattern.factory;

/**
 * Concrete Product: Truck
 * Implements the Vehicle interface for a truck
 */
public class Truck implements Vehicle {
    private boolean isRunning = false;
    private double cargoWeight = 0;

    @Override
    public void start() {
        isRunning = true;
        System.out.println("🚛 Truck diesel engine started with a loud rumble...");
    }

    @Override
    public void stop() {
        isRunning = false;
        System.out.println("🚛 Truck engine stopped.");
    }

    @Override
    public void drive() {
        if (!isRunning) {
            System.out.println("❌ Truck is not running. Start it first!");
            return;
        }
        System.out.println("🚛 Truck is driving heavy-loaded cargo on the road.");
        if (cargoWeight > 0) {
            System.out.println("   Cargo Weight: " + cargoWeight + " tons");
        }
    }

    @Override
    public String getType() {
        return "Truck - Heavy vehicle for cargo transport";
    }

    @Override
    public int getMaxSpeed() {
        return 120; // km/h
    }

    public void loadCargo(double weight) {
        cargoWeight = weight;
        System.out.println("📦 Cargo loaded: " + weight + " tons");
    }
}
