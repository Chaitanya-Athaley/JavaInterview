package com.code.basic.designpattern.factory;

import com.code.basic.designpattern.factory.VehicleFactory.VehicleType;

/**
 * Factory Pattern Demonstration
 * 
 * The Factory Pattern is a creational design pattern that:
 * 1. Provides an interface for creating objects without specifying exact classes
 * 2. Lets subclasses decide what class to instantiate
 * 3. Encapsulates object creation logic
 * 
 * Real-world analogy:
 * A vehicle dealership (factory) creates different types of vehicles based on customer requests
 * without the customer needing to know HOW each vehicle is constructed internally.
 * 
 * When to use:
 * - When a class can't predict the type of objects it needs
 * - When subclasses should specify the objects they create
 * - When object creation logic is complex and should be centralized
 * - When you want loose coupling between client and product classes
 */
public class FactoryPatternDemo {

    public static void main(String[] args) {
        System.out.println("====== FACTORY PATTERN DEMO ======\n");

        // Example 1: Creating vehicles using String type
        System.out.println("--- Example 1: Creating Vehicles Using String Type ---");
        demonstrateWithStringType();

        System.out.println("\n--- Example 2: Creating Vehicles Using Enum Type ---");
        demonstrateWithEnumType();

        System.out.println("\n--- Example 3: Real-world Scenario - Fleet Management ---");
        demonstrateFleetManagement();

        System.out.println("\n--- Example 4: Benefits of Factory Pattern ---");
        demonstrateBenefits();
    }

    /**
     * Demonstrates factory creation using String type
     */
    private static void demonstrateWithStringType() {
        String[] vehicleTypes = {"CAR", "TRUCK", "MOTORCYCLE"};

        for (String type : vehicleTypes) {
            Vehicle vehicle = VehicleFactory.createVehicle(type);
            if (vehicle != null) {
                System.out.println("\n✓ Created: " + vehicle.getType());
                System.out.println("  Max Speed: " + vehicle.getMaxSpeed() + " km/h");
            }
        }
    }

    /**
     * Demonstrates factory creation using Enum type (type-safe approach)
     */
    private static void demonstrateWithEnumType() {
        for (VehicleType type : VehicleType.values()) {
            Vehicle vehicle = VehicleFactory.createVehicle(type);

            System.out.println("\n✓ Created: " + vehicle.getType());
            System.out.println("  Type: " + type.getDisplayName());
            System.out.println("  Max Speed: " + vehicle.getMaxSpeed() + " km/h");

            // Demonstrate vehicle operations
            vehicle.start();
            vehicle.drive();
            vehicle.stop();
        }
    }

    /**
     * Real-world scenario: Fleet management system
     */
    private static void demonstrateFleetManagement() {
        System.out.println("\n🏭 Vehicle Dealership - Creating a Fleet\n");

        // Fleet requirements
        int[] requirements = {5, 3, 2}; // 5 cars, 3 trucks, 2 motorcycles
        VehicleType[] types = {VehicleType.CAR, VehicleType.TRUCK, VehicleType.MOTORCYCLE};

        Fleet fleet = new Fleet();

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < requirements[i]; j++) {
                Vehicle vehicle = VehicleFactory.createVehicle(types[i]);
                fleet.addVehicle(vehicle);
            }
        }

        fleet.displayFleet();
        fleet.startAllVehicles();
    }

    /**
     * Demonstrates the benefits of the Factory Pattern
     */
    private static void demonstrateBenefits() {
        System.out.println("\n✨ Key Benefits of Factory Pattern:");
        System.out.println("\n1. ENCAPSULATION");
        System.out.println("   - Object creation logic is hidden from the client");
        System.out.println("   - Client doesn't need to know about Car, Truck, Motorcycle classes");

        System.out.println("\n2. LOOSE COUPLING");
        System.out.println("   - Client depends on Vehicle interface, not concrete classes");
        System.out.println("   - Easy to add new vehicle types without changing client code");

        System.out.println("\n3. CENTRALIZED CREATION");
        System.out.println("   - All object creation happens in one place");
        System.out.println("   - Easy to add validation or logging logic");

        System.out.println("\n4. FLEXIBILITY");
        System.out.println("   - Can easily switch implementations");
        System.out.println("   - Supports principle: \"Program to interface, not implementation\"");

        System.out.println("\n5. TESTABILITY");
        System.out.println("   - Easy to mock or substitute factory for unit tests");

        System.out.println("\n6. TYPE SAFETY");
        System.out.println("   - Using enum approach eliminates string-based errors");
    }

    /**
     * Inner class: Fleet management using Factory Pattern
     */
    static class Fleet {
        java.util.List<Vehicle> vehicles = new java.util.ArrayList<>();

        void addVehicle(Vehicle vehicle) {
            vehicles.add(vehicle);
        }

        void displayFleet() {
            System.out.println("Fleet Summary:");
            System.out.println("Total Vehicles: " + vehicles.size());
            int carCount = 0, truckCount = 0, motorcycleCount = 0;

            for (Vehicle v : vehicles) {
                if (v instanceof Car) carCount++;
                else if (v instanceof Truck) truckCount++;
                else if (v instanceof Motorcycle) motorcycleCount++;
            }

            System.out.println("  - Cars: " + carCount);
            System.out.println("  - Trucks: " + truckCount);
            System.out.println("  - Motorcycles: " + motorcycleCount);
        }

        void startAllVehicles() {
            System.out.println("\n🚀 Starting all vehicles...\n");
            for (int i = 0; i < Math.min(2, vehicles.size()); i++) {
                Vehicle v = vehicles.get(i);
                System.out.println("Vehicle " + (i + 1) + ":");
                v.start();
                v.drive();
            }
            System.out.println("... (others starting) ...");
        }
    }
}
