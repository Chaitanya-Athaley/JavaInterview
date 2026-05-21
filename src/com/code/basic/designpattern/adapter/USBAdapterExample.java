package com.code.basic.designpattern.adapter;

/**
 * ADAPTER PATTERN - USB Charger Example
 * 
 * Adapter Pattern: Converts the interface of a class into another interface
 * clients expect. Adapter lets classes work together that couldn't otherwise
 * because of incompatible interfaces.
 * 
 * Real-world analogy:
 * You have an old device that uses USB Type-A (iPhone 6).
 * New devices use USB Type-C (iPhone 15).
 * You buy a USB Type-C to Type-A adapter to make them compatible.
 * 
 * The adapter doesn't change the device, it just bridges the gap.
 */

// ============ OLD INTERFACE (Legacy System) ============

/**
 * Old USB Type-A Interface
 * This is what old devices expect
 */
interface USBTypeA {
    void chargeWithTypeA();
}

/**
 * Old Device using USB Type-A
 */
class OldLaptop implements USBTypeA {
    @Override
    public void chargeWithTypeA() {
        System.out.println("💻 Old Laptop: Charging via USB Type-A... 🔌");
    }
}

class OldPhone implements USBTypeA {
    @Override
    public void chargeWithTypeA() {
        System.out.println("📱 Old iPhone 6: Charging via USB Type-A... 🔌");
    }
}

// ============ NEW INTERFACE (Incompatible System) ============

/**
 * New USB Type-C Interface
 * Modern devices use this, but it's incompatible with Type-A
 */
interface USBTypeC {
    void chargeWithTypeC();
}

/**
 * New Device using USB Type-C
 */
class ModernPhone implements USBTypeC {
    @Override
    public void chargeWithTypeC() {
        System.out.println("📱 iPhone 15: Ready to charge with USB Type-C! ⚡");
    }
}

class ModernLaptop implements USBTypeC {
    @Override
    public void chargeWithTypeC() {
        System.out.println("💻 MacBook Pro: Ready to charge with USB Type-C! ⚡");
    }
}

// ============ THE ADAPTER ============

/**
 * Adapter: USB Type-C to Type-A Converter
 * 
 * This adapter makes a USB Type-C device work with a Type-A charger.
 * It "adapts" the new interface (Type-C) to the old interface (Type-A).
 */
class USBTypeCtoAAdapter implements USBTypeA {
    private USBTypeC modernDevice;

    public USBTypeCtoAAdapter(USBTypeC modernDevice) {
        this.modernDevice = modernDevice;
    }

    /**
     * Implements old interface but delegates to new interface
     */
    @Override
    public void chargeWithTypeA() {
        System.out.println("🔌 Adapter: Converting USB Type-A to Type-C...");
        modernDevice.chargeWithTypeC();
        System.out.println("✓ Charging successful via adapter!");
    }
}

// ============ CLASS ADAPTER (Alternative using inheritance) ============

/**
 * Alternative: Class Adapter using Inheritance
 * Adapts using inheritance instead of composition
 * 
 * (Less recommended - violates single responsibility principle)
 */
class HybridUSBAdapter extends OldPhone implements USBTypeC {
    
    @Override
    public void chargeWithTypeC() {
        System.out.println("🔌 Hybrid Adapter: Converting Type-C to Type-A...");
        chargeWithTypeA();
    }

    @Override
    public void chargeWithTypeA() {
        System.out.println("📱 Hybrid Device: Charging via converted adapter... 🔌");
    }
}
