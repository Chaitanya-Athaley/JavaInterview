package com.code.basic.designpattern.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Advanced Factory Pattern Examples
 * 
 * Demonstrates various factory pattern implementations and variations
 */
public class AdvancedFactoryPatterns {

    /**
     * Example 1: Registry-Based Factory
     * Uses a map to store vehicle classes for dynamic registration
     */
    static class RegistryFactory {
        private static final Map<String, Class<? extends Vehicle>> registry = new HashMap<>();

        static {
            // Register vehicle types
            registry.put("CAR", Car.class);
            registry.put("TRUCK", Truck.class);
            registry.put("MOTORCYCLE", Motorcycle.class);
        }

        public static Vehicle createVehicle(String type) {
            Class<? extends Vehicle> vehicleClass = registry.get(type.toUpperCase());
            if (vehicleClass == null) {
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
            }
            try {
                return vehicleClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create vehicle: " + type, e);
            }
        }

        public static void registerVehicle(String type, Class<? extends Vehicle> vehicleClass) {
            registry.put(type.toUpperCase(), vehicleClass);
        }

        public static void listAvailableVehicles() {
            System.out.println("Available Vehicles: " + registry.keySet());
        }
    }

    /**
     * Example 2: Lazy Initialization Factory
     * Creates instances only when first requested and caches them
     */
    static class LazyInitializationFactory {
        private static final Map<String, Vehicle> cache = new HashMap<>();

        public static Vehicle createVehicle(String type) {
            String key = type.toUpperCase();
            
            // Return cached instance if available
            if (cache.containsKey(key)) {
                System.out.println("📦 Returning cached " + key + " from pool...");
                return cache.get(key);
            }

            // Create new instance
            Vehicle vehicle = VehicleFactory.createVehicle(type);
            cache.put(key, vehicle);
            System.out.println("✨ Created and cached new " + key);
            return vehicle;
        }

        public static void clearCache() {
            cache.clear();
            System.out.println("Cache cleared.");
        }
    }

    /**
     * Example 3: Factory with Builder Pattern
     * Combines factory with builder for more complex object creation
     */
    static class CustomVehicleFactory {
        public static LuxuryCar buildLuxuryCar() {
            return new LuxuryCar.Builder()
                    .setMaxSpeed(240)
                    .setFeature("Climate Control")
                    .setFeature("Premium Sound System")
                    .setFeature("Leather Seats")
                    .build();
        }
    }

    /**
     * Advanced Vehicle: LuxuryCar with builder pattern
     */
    static class LuxuryCar implements Vehicle {
        private int maxSpeed = 200;
        private java.util.List<String> features = new java.util.ArrayList<>();
        private boolean isRunning = false;

        public static class Builder {
            private int maxSpeed = 200;
            private java.util.List<String> features = new java.util.ArrayList<>();

            public Builder setMaxSpeed(int speed) {
                this.maxSpeed = speed;
                return this;
            }

            public Builder setFeature(String feature) {
                this.features.add(feature);
                return this;
            }

            public LuxuryCar build() {
                LuxuryCar car = new LuxuryCar();
                car.maxSpeed = this.maxSpeed;
                car.features = this.features;
                return car;
            }
        }

        @Override
        public void start() {
            isRunning = true;
            System.out.println("🚗 Luxury Car starting with automated sequence...");
        }

        @Override
        public void stop() {
            isRunning = false;
            System.out.println("🚗 Luxury Car gracefully stopping...");
        }

        @Override
        public void drive() {
            if (!isRunning) {
                System.out.println("❌ Car is not running. Start it first!");
                return;
            }
            System.out.println("🚗 Driving in comfort with features: " + features);
        }

        @Override
        public String getType() {
            return "Luxury Car";
        }

        @Override
        public int getMaxSpeed() {
            return maxSpeed;
        }
    }

    /**
     * Example 4: Parametric Factory
     * Uses parameters to customize object creation
     */
    static class ParametricVehicleFactory {
        public static Vehicle createVehicle(VehicleConfig config) {
            Vehicle vehicle;

            switch (config.type.toUpperCase()) {
                case "TRUCK":
                    Truck truck = new Truck();
                    if (config.cargoWeight > 0) {
                        truck.loadCargo(config.cargoWeight);
                    }
                    vehicle = truck;
                    break;
                case "CAR":
                    vehicle = new Car();
                    break;
                case "MOTORCYCLE":
                    vehicle = new Motorcycle();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type: " + config.type);
            }

            return vehicle;
        }
    }

    /**
     * Configuration class for parametric factory
     */
    static class VehicleConfig {
        String type;
        double cargoWeight;
        int maxSpeed;

        public VehicleConfig(String type) {
            this.type = type;
        }

        public VehicleConfig setCargoWeight(double weight) {
            this.cargoWeight = weight;
            return this;
        }

        public VehicleConfig setMaxSpeed(int speed) {
            this.maxSpeed = speed;
            return this;
        }
    }

    /**
     * Demo of advanced factory patterns
     */
    public static void main(String[] args) {
        System.out.println("====== ADVANCED FACTORY PATTERNS ======\n");

        // Example 1: Registry-Based Factory
        System.out.println("--- Example 1: Registry-Based Factory ---");
        demoRegistryFactory();

        // Example 2: Lazy Initialization Factory
        System.out.println("\n--- Example 2: Lazy Initialization Factory ---");
        demoLazyFactory();

        // Example 3: Factory + Builder Pattern
        System.out.println("\n--- Example 3: Factory with Builder Pattern ---");
        demoFactoryWithBuilder();

        // Example 4: Parametric Factory
        System.out.println("\n--- Example 4: Parametric Factory ---");
        demoParametricFactory();
    }

    private static void demoRegistryFactory() {
        RegistryFactory.listAvailableVehicles();
        Vehicle car = RegistryFactory.createVehicle("CAR");
        car.start();
        car.drive();
    }

    private static void demoLazyFactory() {
        Vehicle v1 = LazyInitializationFactory.createVehicle("CAR");
        Vehicle v2 = LazyInitializationFactory.createVehicle("CAR");
        System.out.println("Same instance? " + (v1 == v2));
    }

    private static void demoFactoryWithBuilder() {
        LuxuryCar luxury = CustomVehicleFactory.buildLuxuryCar();
        System.out.println("Max Speed: " + luxury.getMaxSpeed());
        luxury.start();
        luxury.drive();
    }

    private static void demoParametricFactory() {
        VehicleConfig truckConfig = new VehicleConfig("TRUCK")
                .setCargoWeight(50)
                .setMaxSpeed(120);

        Vehicle truck = ParametricVehicleFactory.createVehicle(truckConfig);
        truck.start();
        truck.drive();
    }
}
