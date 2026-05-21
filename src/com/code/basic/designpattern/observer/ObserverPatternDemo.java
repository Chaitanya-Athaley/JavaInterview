package com.code.basic.designpattern.observer;

/**
 * OBSERVER PATTERN DEMO - Weather Station System
 * 
 * This demo shows:
 * 1. How to attach observers to a subject
 * 2. How observers are notified when subject state changes
 * 3. How to detach observers
 * 4. Loose coupling between subject and observers
 */
public class ObserverPatternDemo {

    public static void main(String[] args) {
        System.out.println("====== OBSERVER PATTERN DEMO ======\n");

        // Example 1: Basic Observer Notification
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 1: Setting up Weather Display Systems");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateBasicSubscription();

        // Example 2: Multiple Weather Changes
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 2: Multiple Weather Changes");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateMultipleUpdates();

        // Example 3: Observer Unsubscription
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 3: Observer Detachment");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateDetachment();

        // Example 4: Dynamic Observer Addition
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 4: Dynamic Observer Registration");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateDynamicObservers();

        // Example 5: Benefits
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("WHY OBSERVER PATTERN IS USEFUL?");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateBenefits();
    }

    private static void demonstrateBasicSubscription() {
        // Create the subject (weather station)
        WeatherStation station = new WeatherStation();

        // Create observers
        Observer phone = new PhoneDisplay("iPhone 14");
        Observer web = new WebDisplay();
        Observer tv = new TVDisplay();

        // Attach observers (subscribe)
        System.out.println("📡 Subscribing displays to weather station...\n");
        station.attach(phone);
        station.attach(web);
        station.attach(tv);

        station.displaySubscriberCount();

        // Set weather data - all observers get notified
        station.setWeatherData(25, 60, 1013);
    }

    private static void demonstrateMultipleUpdates() {
        WeatherStation station = new WeatherStation();

        // Setup observers
        Observer phone = new PhoneDisplay("Samsung Galaxy");
        Observer web = new WebDisplay();
        Observer alarm = new AlarmSystem();

        station.attach(phone);
        station.attach(web);
        station.attach(alarm);

        // First measurement
        station.setWeatherData(28, 55, 1012);

        // Second measurement - all observers notified again
        station.setWeatherData(35, 70, 1010);

        // Extreme temperature - alarm triggers
        station.setWeatherData(42, 80, 1008);
    }

    private static void demonstrateDetachment() {
        WeatherStation station = new WeatherStation();

        Observer phone = new PhoneDisplay("OnePlus");
        Observer web = new WebDisplay();
        Observer dataLogger = new DataLogger();

        System.out.println("Initial subscription:");
        station.attach(phone);
        station.attach(web);
        station.attach(dataLogger);

        station.setWeatherData(22, 50, 1015);

        // Detach an observer
        System.out.println("\n🔌 Detaching Phone Display...\n");
        station.detach(phone);

        // Phone won't get updates anymore
        station.setWeatherData(26, 65, 1012);
    }

    private static void demonstrateDynamicObservers() {
        WeatherStation station = new WeatherStation();

        Observer tv = new TVDisplay();
        Observer dataLogger = new DataLogger();

        System.out.println("Step 1: Initial setup");
        station.attach(tv);
        station.attach(dataLogger);
        station.setWeatherData(20, 40, 1014);

        System.out.println("\n\nStep 2: Adding alarm system dynamically");
        Observer alarm = new AlarmSystem();
        station.attach(alarm);
        station.setWeatherData(38, 75, 1009);

        System.out.println("\n\nStep 3: Adding phone display dynamically");
        Observer phone = new PhoneDisplay("Pixel 7");
        station.attach(phone);
        station.setWeatherData(18, 45, 1016);
    }

    private static void demonstrateBenefits() {
        System.out.println("\n✨ KEY BENEFITS OF OBSERVER PATTERN:\n");

        System.out.println("1. 📌 LOOSE COUPLING");
        System.out.println("   - Subject doesn't know concrete observer classes");
        System.out.println("   - Observers only know about Subject interface");
        System.out.println("   - Easy to change implementations\n");

        System.out.println("2. 🎯 DYNAMIC SUBSCRIPTION");
        System.out.println("   - Observers can subscribe/unsubscribe at runtime");
        System.out.println("   - No need to restart system\n");

        System.out.println("3. 🔄 BROADCAST COMMUNICATION");
        System.out.println("   - One subject → Many observers");
        System.out.println("   - All get notified automatically\n");

        System.out.println("4. 🧩 SINGLE RESPONSIBILITY");
        System.out.println("   - Subject: Maintain state & notify");
        System.out.println("   - Observers: React to notifications");
        System.out.println("   - Each has one reason to change\n");

        System.out.println("5. 🔌 PLUG & PLAY");
        System.out.println("   - Add new observers without modifying subject");
        System.out.println("   - Subject code remains unchanged\n");

        System.out.println("6. 🌳 EVENT-DRIVEN ARCHITECTURE");
        System.out.println("   - Perfect for event handling systems");
        System.out.println("   - Used in GUI frameworks, event buses, etc.\n");

        System.out.println("7. 🧪 EASY TO TEST");
        System.out.println("   - Can mock observers for testing");
        System.out.println("   - Can test subject independently\n");
    }
}
