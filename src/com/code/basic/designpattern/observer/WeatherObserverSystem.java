package com.code.basic.designpattern.observer;

/**
 * OBSERVER PATTERN - Weather Station Example
 * 
 * Observer Pattern: When one object changes state, all its dependents
 * are notified automatically without being tightly coupled.
 * 
 * Real-world analogy:
 * Imagine a weather station that measures temperature, humidity, and pressure.
 * Different displays (phone, web, TV, alarm) need to be updated whenever weather changes.
 * 
 * Instead of the weather station calling each display directly (tightly coupled),
 * displays register themselves to be notified (loosely coupled).
 */

// ============ OBSERVER INTERFACE ============

/**
 * Observer Interface
 * Any object wanting to be notified about weather changes must implement this
 */
interface Observer {
    void update(double temperature, double humidity, double pressure);
}

// ============ CONCRETE OBSERVERS ============

/**
 * Observer 1: Phone Display
 * Shows weather data on mobile phone
 */
class PhoneDisplay implements Observer {
    private String deviceName;
    private double temperature;
    private double humidity;

    public PhoneDisplay(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public void update(double temperature, double humidity, double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    private void display() {
        System.out.println("📱 " + deviceName + " Display:");
        System.out.println("   Temperature: " + temperature + "°C");
        System.out.println("   Humidity: " + humidity + "%");
    }
}

/**
 * Observer 2: Web Display
 * Shows weather data on web portal
 */
class WebDisplay implements Observer {
    private double temperature;
    private double humidity;
    private double pressure;

    @Override
    public void update(double temperature, double humidity, double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }

    private void display() {
        System.out.println("🌐 Web Portal Display:");
        System.out.println("   Temperature: " + temperature + "°C");
        System.out.println("   Humidity: " + humidity + "%");
        System.out.println("   Pressure: " + pressure + " hPa");
    }
}

/**
 * Observer 3: TV Display
 * Shows weather data on television
 */
class TVDisplay implements Observer {
    @Override
    public void update(double temperature, double humidity, double pressure) {
        display(temperature);
    }

    private void display(double temp) {
        System.out.println("📺 TV Weather Channel:");
        if (temp > 30) {
            System.out.println("   ⚠️  HOT: " + temp + "°C - Stay hydrated!");
        } else if (temp < 10) {
            System.out.println("   ❄️  COLD: " + temp + "°C - Wear a sweater!");
        } else {
            System.out.println("   ✓ PLEASANT: " + temp + "°C - Great weather!");
        }
    }
}

/**
 * Observer 4: Alarm System
 * Alerts if temperature is extreme
 */
class AlarmSystem implements Observer {
    @Override
    public void update(double temperature, double humidity, double pressure) {
        if (temperature > 40) {
            System.out.println("🔴 🚨 ALARM: EXTREME HEAT! Temperature: " + temperature + "°C");
        } else if (temperature < -10) {
            System.out.println("🔵 🚨 ALARM: EXTREME COLD! Temperature: " + temperature + "°C");
        }
    }
}

/**
 * Observer 5: Data Logger
 * Logs weather data to file/database
 */
class DataLogger implements Observer {
    private int logCount = 0;

    @Override
    public void update(double temperature, double humidity, double pressure) {
        logCount++;
        System.out.println("💾 Data Logger [Log #" + logCount + "]:");
        System.out.println("   Recorded: T=" + temperature + "°C, H=" + humidity + "%, P=" + pressure + " hPa");
    }
}

// ============ SUBJECT INTERFACE (OBSERVABLE) ============

/**
 * Subject Interface (Observable)
 * Maintains list of observers and notifies them
 */
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// ============ CONCRETE SUBJECT ============

/**
 * Weather Station - The Observable/Subject
 * Maintains weather state and notifies all observers
 */
class WeatherStation implements Subject {
    private double temperature;
    private double humidity;
    private double pressure;
    private java.util.List<Observer> observers;

    public WeatherStation() {
        this.observers = new java.util.ArrayList<>();
    }

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("✓ Observer attached: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void detach(Observer observer) {
        if (observers.remove(observer)) {
            System.out.println("✗ Observer detached: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    /**
     * When weather measurements change, update state and notify observers
     */
    public void setWeatherData(double temperature, double humidity, double pressure) {
        System.out.println("\n📊 Weather Station measured new data:");
        System.out.println("   Temperature: " + temperature + "°C");
        System.out.println("   Humidity: " + humidity + "%");
        System.out.println("   Pressure: " + pressure + " hPa");
        System.out.println("   Notifying all observers...\n");

        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }

    public void displaySubscriberCount() {
        System.out.println("📢 Total observers: " + observers.size());
    }
}
