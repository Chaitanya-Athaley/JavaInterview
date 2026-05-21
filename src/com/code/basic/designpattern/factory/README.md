# Factory Pattern - Best Practices Example

## 📋 Overview
This example demonstrates the **Factory Method Pattern**, a creational design pattern that provides an interface for creating objects without specifying their exact classes.

## 🏗️ Architecture

### Class Structure
```
Vehicle (Interface)
    ├── Car (implements Vehicle)
    ├── Truck (implements Vehicle)
    └── Motorcycle (implements Vehicle)

VehicleFactory (Creator)
    └── Creates Vehicle instances based on type

FactoryPatternDemo (Client)
    └── Uses VehicleFactory to create and use vehicles
```

## 📁 Files Description

| File | Purpose |
|------|---------|
| `Vehicle.java` | Product interface defining vehicle contract |
| `Car.java` | Concrete product - Car implementation |
| `Truck.java` | Concrete product - Truck implementation |
| `Motorcycle.java` | Concrete product - Motorcycle implementation |
| `VehicleFactory.java` | Factory class creating vehicle instances |
| `FactoryPatternDemo.java` | Demo showing all usage patterns |

## 🎯 Key Benefits

### 1. **Encapsulation**
- Object creation logic is hidden from clients
- Clients work with interface, not concrete classes

### 2. **Loose Coupling**
- Client depends on `Vehicle` interface only
- Easy to add new vehicle types without modifying existing code

### 3. **Centralized Creation**
- Single point of control for object instantiation
- Easy to add validation, logging, or initialization logic

### 4. **Flexibility**
- Switch implementations without changing client code
- Follows "Program to interface, not implementation"

### 5. **Type Safety**
- Enum-based factory eliminates string-based errors
- Compile-time checking of valid types

## 💡 Usage Examples

### Basic Factory Usage
```java
// Using String type (simple but less safe)
Vehicle car = VehicleFactory.createVehicle("CAR");

// Using Enum (type-safe)
Vehicle truck = VehicleFactory.createVehicle(VehicleType.TRUCK);
```

### Real-world Scenario
```java
Vehicle[] fleet = {
    VehicleFactory.createVehicle(VehicleType.CAR),
    VehicleFactory.createVehicle(VehicleType.TRUCK),
    VehicleFactory.createVehicle(VehicleType.MOTORCYCLE)
};

for (Vehicle v : fleet) {
    v.start();
    v.drive();
    v.stop();
}
```

## 🔄 Pattern Flow

```
Client Code
    ↓
VehicleFactory.createVehicle(type)
    ↓
    ├─→ if type == "CAR" ──→ new Car()
    ├─→ if type == "TRUCK" ──→ new Truck()
    └─→ if type == "MOTORCYCLE" ──→ new Motorcycle()
    ↓
Vehicle reference (polymorphic)
    ↓
Client uses interface methods:
  - start()
  - drive()
  - stop()
```

## 🆚 Factory Pattern Types

### 1. **Simple Factory** (Demonstrated here)
- Static factory method
- Single responsibility
- Good for small number of types

### 2. **Factory Method Pattern**
- Each creator has its own factory method
- Subclasses decide which class to instantiate
- More extensible

### 3. **Abstract Factory Pattern**
- Creates families of related objects
- Ensures consistency across object families

## ✅ When to Use

✓ When a class can't predict the type of objects it needs  
✓ When subclasses should specify objects they create  
✓ When object creation logic is complex  
✓ When you want loose coupling  
✓ When you have multiple similar object types  

## ❌ When NOT to Use

✗ When you have only one simple product  
✗ When creation logic is trivial  
✗ When added complexity isn't justified  

## 🧪 Running the Demo

```bash
javac VehicleFactory.java Vehicle.java Car.java Truck.java Motorcycle.java FactoryPatternDemo.java
java FactoryPatternDemo
```

## 📊 Expected Output

```
====== FACTORY PATTERN DEMO ======

--- Example 1: Creating Vehicles Using String Type ---
✓ Created: Car - 4 wheels, comfortable for passengers
  Max Speed: 200 km/h
...

--- Example 3: Real-world Scenario - Fleet Management ---
🏭 Vehicle Dealership - Creating a Fleet
Fleet Summary:
  - Cars: 5
  - Trucks: 3
  - Motorcycles: 2
...
```

## 🔗 Related Patterns

- **Abstract Factory**: Creates families of related objects
- **Builder**: Complex object creation with step-by-step construction
- **Singleton**: Creates single instance with lazy initialization
- **Prototype**: Creates objects by cloning existing instances

## 💻 Best Practices

1. **Use interfaces** for loose coupling
2. **Use enums** for type-safe factories
3. **Hide concrete implementations** from clients
4. **Document factory methods** clearly
5. **Consider immutability** of created objects
6. **Use descriptive names** for factory methods

## 🎓 Interview Tips

- Explain why factory is better than `new` keyword
- Demonstrate loose coupling benefits
- Show real-world scenarios
- Discuss trade-offs vs complexity
- Know differences between factory types
