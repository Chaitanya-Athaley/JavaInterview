# Adapter Design Pattern - Simple Guide

## 🎯 What is Adapter Pattern?

**In Simple Words:**
Adapter Pattern makes two incompatible interfaces work together by creating a bridge between them.

Think of it like:
- USB Type-C phone with USB Type-A charger
- You need an adapter to make them work together
- The adapter doesn't change the phone or charger, just bridges the gap

## 🔌 Real-World Analogy: USB Adapter

```
Old USB Type-A Charger
         △
         │ expects chargeWithTypeA()
         │
┌────────┴────────┐
│                 │
Works with:       Doesn't work with:
├─ Old iPhone     └─ iPhone 15 (Type-C)
├─ Old Laptop     
└─ ...

iPhone 15 (USB Type-C)
has chargeWithTypeC() method

❌ PROBLEM: Interfaces don't match!

SOLUTION: USB Type-C to Type-A Adapter
         ↓
Connect adapter between charger and phone
         ↓
✅ Now they work together!
```

## 🏗️ Structure

```
┌──────────────────┐
│ Old Interface    │
│ (clientExpected) │
└────────△─────────┘
         │
    ┌────┴──────────────────┐
    │  Adapter              │
    │ ┌────────────────┐    │
    │ │ wraps/adapts  │    │
    │ └────────────────┘    │
    └────────┬──────────────┘
             │
         ┌───▼──────────────┐
         │ New Interface    │
         │ (Service)        │
         └──────────────────┘
```

## 📁 Files Description

| File | Purpose |
|------|---------|
| `USBAdapterExample.java` | Interfaces + Devices + Adapter implementation |
| `AdapterPatternDemo.java` | Demo showing problems and solutions |

## 🔑 Key Components

### 1. Old/Legacy Interface
```java
interface USBTypeA {
    void chargeWithTypeA();
}

class OldPhone implements USBTypeA { ... }
```

### 2. New/Incompatible Interface
```java
interface USBTypeC {
    void chargeWithTypeC();
}

class ModernPhone implements USBTypeC { ... }
```

### 3. The Adapter
```java
class USBTypeCtoAAdapter implements USBTypeA {
    private USBTypeC modernDevice;  // Wraps new interface
    
    @Override
    public void chargeWithTypeA() {
        // Adapts old interface to new
        modernDevice.chargeWithTypeC();
    }
}
```

## 💡 How It Works

### Step 1: You have two incompatible interfaces
```java
// Old interface - USB Type-A
interface USBTypeA {
    void chargeWithTypeA();
}

// New interface - USB Type-C
interface USBTypeC {
    void chargeWithTypeC();
}
```

### Step 2: Create adapter that implements old interface
```java
class USBTypeCtoAAdapter implements USBTypeA {
    private USBTypeC device;  // Hold reference to new device
    
    public USBTypeCtoAAdapter(USBTypeC device) {
        this.device = device;
    }
    
    @Override
    public void chargeWithTypeA() {
        // Call new interface using old interface method
        device.chargeWithTypeC();
    }
}
```

### Step 3: Use adapter to make them work together
```java
// New device with Type-C
USBTypeC modernPhone = new ModernPhone();

// Wrap with adapter
USBTypeA adapter = new USBTypeCtoAAdapter(modernPhone);

// Now you can use Type-C phone with Type-A charger!
adapter.chargeWithTypeA();
```

## 🆚 Two Types of Adapters

### 1. Object Adapter (Composition) ✅ RECOMMENDED
```java
class Adapter implements TargetInterface {
    private IncompatibleObject object;  // Has-a relationship
    
    public void targetMethod() {
        object.incompatibleMethod();
    }
}
```

**Benefits:**
- More flexible
- Single responsibility
- Can adapt multiple objects

### 2. Class Adapter (Inheritance) ⚠️ LESS RECOMMENDED
```java
class Adapter extends IncompatibleClass implements TargetInterface {
    public void targetMethod() {
        super.incompatibleMethod();
    }
}
```

**Drawbacks:**
- Tight coupling via inheritance
- Only adapts one class
- Breaks single responsibility

## ✨ Benefits

### 1. **Compatibility**
```java
// Before: Can't mix old and new
// After: Works perfectly with adapter!
USBTypeA charger = new OldCharger();
USBTypeC phone = new ModernPhone();

USBTypeA adapter = new USBTypeCtoAAdapter(phone);
charger.charge(adapter);  // ✓ Works!
```

### 2. **Reusability**
```java
// Reuse old charger with new phone
// No need to buy new charger
```

### 3. **No Changes to Original Classes**
```java
// ModernPhone class unchanged ✓
// OldCharger class unchanged ✓
// Only adapter added!
```

### 4. **Bridge Legacy & New**
```java
// Gradual migration path
// Old system + adapter + New system = works!
```

## 📊 Real-World Examples

| Scenario | Old Interface | New Interface | Adapter |
|----------|---------------|---------------|---------|
| **USB** | USB Type-A | USB Type-C | USB-C to A adapter |
| **Video** | VGA | HDMI | VGA to HDMI converter |
| **Power** | 220V | 110V | Voltage adapter |
| **Data** | SQL Database | NoSQL Database | ORM adapter |
| **API** | SOAP | REST | API adapter |
| **Payment** | PayPal API | Stripe API | Payment adapter |

## 🆚 Adapter vs Other Patterns

| Pattern | Purpose | Relationship |
|---------|---------|--------------|
| **Adapter** | Make incompatible interfaces work | Existing classes |
| **Decorator** | Add behavior to objects | Same interface |
| **Facade** | Simplify complex subsystem | Multiple objects |
| **Bridge** | Separate abstraction from implementation | Design time |
| **Strategy** | Switch algorithms | Same contract |

## ✅ When to Use

✅ Use Adapter Pattern when:
- You have incompatible interfaces
- You need to integrate old and new systems
- You want to reuse existing classes
- You can't modify original classes
- You need a compatibility layer

❌ Don't use if:
- Interfaces are designed to be incompatible
- Modification of original classes is acceptable
- Added complexity isn't justified

## 🧪 Running the Example

```bash
# Compile
javac USBAdapterExample.java AdapterPatternDemo.java

# Run
java AdapterPatternDemo
```

## 📊 Example Output

```
====== ADAPTER PATTERN DEMO ======

═══════════════════════════════════════════════════════
SCENARIO 1: Understanding the Problem
═══════════════════════════════════════════════════════

📋 Problem: Incompatible Interfaces

Old devices with Type-A interface:
💻 Old Laptop: Charging via USB Type-A... 🔌
📱 Old iPhone 6: Charging via USB Type-A... 🔌

New devices with Type-C interface:
📱 iPhone 15: Ready to charge with USB Type-C! ⚡

❌ Problem: Can't use old charger for new phone!
   - Old charger expects: chargeWithTypeA()
   - New phone has: chargeWithTypeC()
   - Interfaces are incompatible!

═══════════════════════════════════════════════════════
SCENARIO 2: Using Adapter to Solve Problem
═══════════════════════════════════════════════════════

✅ Solution: Use USB Type-C to Type-A Adapter

🔌 Creating USB Type-C to Type-A Adapter...

💡 Using modern phone with adapter:
🔌 Adapter: Converting USB Type-A to Type-C...
📱 iPhone 15: Ready to charge with USB Type-C! ⚡
✓ Charging successful via adapter!

✓ Success! Modern device now works with old charger!
```

## 🎓 Interview Tips

1. **Explain problem first**: "Two incompatible interfaces need to work together"
2. **Show USB adapter analogy**: Everyone relates to it
3. **Demonstrate the code**: Object adapter preferred over class adapter
4. **Show benefits**: Reusability, no modifications needed
5. **Compare with alternatives**: Why not just modify the class?
6. **Real-world examples**: Payment gateways, database adapters

## 💻 Adding New Device (Easy!)

```java
class NewTablet implements USBTypeC {
    @Override
    public void chargeWithTypeC() {
        System.out.println("📱 Tablet: Charging with Type-C!");
    }
}

// Use with adapter
USBTypeA tabletAdapter = new USBTypeCtoAAdapter(new NewTablet());
tabletAdapter.chargeWithTypeA();  // Works!

// No changes to adapter or charger class!
```

## 🎯 Main Takeaway

**"Adapter Pattern = Bridge between incompatible interfaces without changing their code"**

Perfect for:
- Hardware adapters (USB, HDMI, Power)
- Software integration (Legacy ↔ New systems)
- API conversions (REST ↔ SOAP)
- Database migrations
- Payment gateway integration

The beauty: Make incompatible things work **without changing them**! ✨
