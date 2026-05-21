package com.code.basic.designpattern.factory;

/**
 * Vehicle Interface - Product interface for the Factory Pattern
 * Defines the contract that all vehicle types must implement
 */
public interface Vehicle {
    void start();
    void stop();
    void drive();
    String getType();
    int getMaxSpeed();
}
