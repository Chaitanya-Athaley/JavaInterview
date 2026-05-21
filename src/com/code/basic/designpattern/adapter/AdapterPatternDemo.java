package com.code.basic.designpattern.adapter;

/**
 * ADAPTER PATTERN DEMO - USB Charger System
 * 
 * This demo shows:
 * 1. Problem: Incompatible interfaces
 * 2. Solution: Using adapter to bridge them
 * 3. Benefits of adapter pattern
 */
public class AdapterPatternDemo {

    public static void main(String[] args) {
        System.out.println("====== ADAPTER PATTERN DEMO ======\n");

        // Example 1: The Problem - Incompatible Interfaces
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 1: Understanding the Problem");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateProblem();

        // Example 2: Solution using Adapter (Object Adapter)
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 2: Using Adapter to Solve Problem");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateSolutionWithAdapter();

        // Example 3: Charging Station (needs Type-A interface)
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 3: Charging Station Challenge");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateChargingStation();

        // Example 4: Multiple Devices and Adapters
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 4: Multiple Modern Devices with Adapters");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateMultipleAdapters();

        // Example 5: Real-world scenarios
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 5: Real-World Adapter Examples");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateRealWorldExamples();

        // Example 6: Benefits
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("WHY ADAPTER PATTERN IS USEFUL?");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateBenefits();
    }

    private static void demonstrateProblem() {
        System.out.println("\n📋 Problem: Incompatible Interfaces\n");

        // Old devices work fine with Type-A
        System.out.println("Old devices with Type-A interface:");
        USBTypeA oldPhone = new OldPhone();
        oldPhone.chargeWithTypeA();

        USBTypeA oldLaptop = new OldLaptop();
        oldLaptop.chargeWithTypeA();

        // New devices have different interface
        System.out.println("\nNew devices with Type-C interface:");
        USBTypeC newPhone = new ModernPhone();
        newPhone.chargeWithTypeC();

        System.out.println("\n❌ Problem: Can't use old charger for new phone!");
        System.out.println("   - Old charger expects: chargeWithTypeA()");
        System.out.println("   - New phone has: chargeWithTypeC()");
        System.out.println("   - Interfaces are incompatible!\n");
    }

    private static void demonstrateSolutionWithAdapter() {
        System.out.println("\n✅ Solution: Use USB Type-C to Type-A Adapter\n");

        // New device
        USBTypeC modernPhone = new ModernPhone();

        // Adapter wraps new device and provides old interface
        System.out.println("🔌 Creating USB Type-C to Type-A Adapter...");
        USBTypeA adapter = new USBTypeCtoAAdapter(modernPhone);

        // Now we can use new device with old interface!
        System.out.println("\n💡 Using modern phone with adapter:");
        adapter.chargeWithTypeA();

        System.out.println("\n✓ Success! Modern device now works with old charger!");
    }

    private static void demonstrateChargingStation() {
        System.out.println("\n🏪 Charging Station Setup:\n");

        System.out.println("📍 Location: Airport Terminal");
        System.out.println("⚡ Available: USB Type-A Charging Points\n");

        // Mixed old and new devices
        System.out.println("--- Devices waiting to charge ---\n");

        // Old devices - can use directly
        System.out.println("Device 1: Old iPhone 6");
        USBTypeA oldPhone = new OldPhone();

        System.out.println("\n Device 2: New iPhone 15");
        USBTypeC newPhone = new ModernPhone();

        // Scenario: All devices need to charge at Type-A station
        System.out.println("\n\n--- Charging at Type-A Station ---\n");

        System.out.println("Charging old iPhone 6:");
        oldPhone.chargeWithTypeA();

        System.out.println("\n\nCharging new iPhone 15 (with adapter):");
        USBTypeA adapterForNewPhone = new USBTypeCtoAAdapter(newPhone);
        adapterForNewPhone.chargeWithTypeA();

        System.out.println("\n✓ Both devices charged successfully with adapter!");
    }

    private static void demonstrateMultipleAdapters() {
        System.out.println("\n🛍️ Tech Store: Using Adapters for Multiple Devices\n");

        // Multiple modern devices
        USBTypeC phone = new ModernPhone();
        USBTypeC laptop = new ModernLaptop();

        // Available old charging points
        System.out.println("📍 Charging Points: Only USB Type-A available\n");

        // Use adapters for each modern device
        System.out.println("Device 1: iPhone 15");
        USBTypeA phoneAdapter = new USBTypeCtoAAdapter(phone);
        phoneAdapter.chargeWithTypeA();

        System.out.println("\nDevice 2: MacBook Pro");
        USBTypeA laptopAdapter = new USBTypeCtoAAdapter(laptop);
        laptopAdapter.chargeWithTypeA();

        System.out.println("\n✓ All modern devices working with old infrastructure!");
    }

    private static void demonstrateRealWorldExamples() {
        System.out.println("\n🌍 Real-World Adapter Patterns:\n");

        System.out.println("1️⃣  PHYSICAL ADAPTERS");
        System.out.println("   - USB Type-C to Type-A");
        System.out.println("   - HDMI to DisplayPort");
        System.out.println("   - Power adapters (voltage converters)\n");

        System.out.println("2️⃣  SOFTWARE ADAPTERS");
        System.out.println("   - Legacy system ↔ New API integration");
        System.out.println("   - Payment Gateway adapters");
        System.out.println("   - Database adapters (SQL to NoSQL)\n");

        System.out.println("3️⃣  FRAMEWORK ADAPTERS");
        System.out.println("   - Spring adapters");
        System.out.println("   - Jakarta EE (formerly J2EE) adapters");
        System.out.println("   - ORM adapters (Hibernate, JPA)\n");

        System.out.println("4️⃣  PROTOCOL ADAPTERS");
        System.out.println("   - HTTP to HTTPS");
        System.out.println("   - REST to GraphQL");
        System.out.println("   - SOAP to REST\n");
    }

    private static void demonstrateBenefits() {
        System.out.println("\n✨ KEY BENEFITS OF ADAPTER PATTERN:\n");

        System.out.println("1. 🔗 COMPATIBILITY");
        System.out.println("   - Make incompatible interfaces work together");
        System.out.println("   - No need to change existing classes\n");

        System.out.println("2. 🔄 REUSABILITY");
        System.out.println("   - Reuse old classes with new systems");
        System.out.println("   - Leverage existing working code\n");

        System.out.println("3. 🧩 SEPARATION OF CONCERNS");
        System.out.println("   - Original class unchanged");
        System.out.println("   - Adaptation logic in adapter\n");

        System.out.println("4. 🏗️  BRIDGE LEGACY & NEW");
        System.out.println("   - Perfect for system migration");
        System.out.println("   - Gradual integration possible\n");

        System.out.println("5. 🎯 SINGLE RESPONSIBILITY");
        System.out.println("   - Adapter has one job: adapt");
        System.out.println("   - Original classes keep their purpose\n");

        System.out.println("6. 🔌 MINIMAL CHANGES");
        System.out.println("   - Add adapter without modifying legacy code");
        System.out.println("   - New code doesn't know about adaptation\n");

        System.out.println("7. 📦 DECOUPLING");
        System.out.println("   - Reduces coupling between systems");
        System.out.println("   - Systems don't need to know each other\n");
    }
}
