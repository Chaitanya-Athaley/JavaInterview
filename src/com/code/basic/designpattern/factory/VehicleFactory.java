package com.code.basic.designpattern.factory;

/**
 * Factory Class - Creates Vehicle objects based on type
 * This implements the Factory Method Pattern
 * 
 * Benefits:
 * - Encapsulates object creation logic
 * - Loose coupling between client and concrete vehicle classes
 * - Easy to add new vehicle types without modifying client code
 * - Centralized creation logic
 */
public class VehicleFactory {

    /**
     * Factory method to create vehicles
     * 
     * @param vehicleType Type of vehicle to create (CAR, TRUCK, MOTORCYCLE)
     * @return Vehicle instance or null if type is unknown
     */
    public static Vehicle createVehicle(String vehicleType) {
        if (vehicleType == null) {
            return null;
        }

        switch (vehicleType.toUpperCase()) {
            case "CAR":
                return new Car();
            case "TRUCK":
                return new Truck();
            case "MOTORCYCLE":
                return new Motorcycle();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }

    /**
     * Overloaded method using enum for type safety
     */
    public static Vehicle createVehicle(VehicleType type) {
        switch (type) {
            case CAR:
                return new Car();
            case TRUCK:
                return new Truck();
            case MOTORCYCLE:
                return new Motorcycle();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }

    /**
     * Enum for type-safe vehicle creation
     */
    public enum VehicleType {
        CAR("Car"),
        TRUCK("Truck"),
        MOTORCYCLE("Motorcycle");

        private final String displayName;

        VehicleType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
