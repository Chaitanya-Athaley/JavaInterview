package com.code.java17features;

/**
 * SEALED CLASSES - Java 17
 * 
 * Purpose: Control which classes can extend (subclass) a sealed class
 * Benefit: Restrict inheritance hierarchy for better type safety and optimization
 * 
 * Syntax: public sealed class ClassName permits SubClass1, SubClass2, ... { }
 * 
 * Permitted subclasses must be:
 * 1. Final - cannot be further extended
 * 2. Sealed - can have its own permitted subclasses
 * 3. Non-sealed - can be extended by any class
 */

// Example 1: Basic Sealed Class
sealed abstract class Vehicle permits Car, Motorcycle, Truck {
    private String color;
    private int speed;
    
    public Vehicle(String color, int speed) {
        this.color = color;
        this.speed = speed;
    }
    
    public abstract void drive();
    
    public void displayInfo() {
        System.out.println("Color: " + color + ", Speed: " + speed + " km/h");
    }
    
    public String getColor() {
        return color;
    }
    
    public int getSpeed() {
        return speed;
    }
}

// Final subclass - cannot be extended further
final class Car extends Vehicle {
    private int numberOfDoors;
    
    public Car(String color, int speed, int numberOfDoors) {
        super(color, speed);
        this.numberOfDoors = numberOfDoors;
    }
    
    @Override
    public void drive() {
        System.out.println("Car is driving on road with " + numberOfDoors + " doors");
    }
}

// Final subclass
final class Motorcycle extends Vehicle {
    private boolean hasSideCar;
    
    public Motorcycle(String color, int speed, boolean hasSideCar) {
        super(color, speed);
        this.hasSideCar = hasSideCar;
    }
    
    @Override
    public void drive() {
        System.out.println("Motorcycle is driving " + (hasSideCar ? "with" : "without") + " a sidecar");
    }
}

// Final subclass
final class Truck extends Vehicle {
    private double loadCapacity;
    
    public Truck(String color, int speed, double loadCapacity) {
        super(color, speed);
        this.loadCapacity = loadCapacity;
    }
    
    @Override
    public void drive() {
        System.out.println("Truck is driving with " + loadCapacity + " tons load capacity");
    }
}


// Example 2: Sealed Class with Sealed Subclass
sealed class Creature permits Canine, Feline, WildCreature {
    private String name;
    
    public Creature(String name) {
        this.name = name;
    }
    
    public void makeSound() {
        System.out.println("Creature sound");
    }
    
    public String getName() {
        return name;
    }
}

final class Canine extends Creature {
    public Canine(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println("Woof! Woof!");
    }
}

final class Feline extends Creature {
    public Feline(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println("Meow! Meow!");
    }
}

// Sealed subclass with its own permitted classes
sealed class WildCreature extends Creature permits Lion, Tiger {
    public WildCreature(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println("Growl!");
    }
}

final class Lion extends WildCreature {
    public Lion(String name) {
        super(name);
    }
}

final class Tiger extends WildCreature {
    public Tiger(String name) {
        super(name);
    }
}


// Demo class
class SealedClassesDemo {
    
    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("      SEALED CLASSES - Java 17 Feature");
        System.out.println("═══════════════════════════════════════════════════\n");
        
        demonstrateVehicles();
        System.out.println();
        demonstrateAnimals();
        System.out.println();
        demonstrateTypeChecking();
    }
    
    private static void demonstrateVehicles() {
        System.out.println("EXAMPLE 1: Vehicle Hierarchy with Sealed Classes");
        System.out.println("───────────────────────────────────────────────\n");
        
        Vehicle car = new Car("Red", 120, 4);
        Vehicle bike = new Motorcycle("Black", 150, false);
        Vehicle truck = new Truck("White", 80, 50.5);
        
        System.out.println("Car:");
        car.displayInfo();
        car.drive();
        
        System.out.println("\nMotorcycle:");
        bike.displayInfo();
        bike.drive();
        
        System.out.println("\nTruck:");
        truck.displayInfo();
        truck.drive();
        
        System.out.println("\n✓ Only Car, Motorcycle, and Truck can extend Vehicle");
        System.out.println("✓ Each subclass is FINAL - cannot be extended further");
    }
    
    private static void demonstrateAnimals() {
        System.out.println("\n\nEXAMPLE 2: Creature Hierarchy with Sealed Subclass");
        System.out.println("───────────────────────────────────────────────\n");
        
        Creature dog = new Canine("Buddy");
        Creature cat = new Feline("Whiskers");
        Creature lion = new Lion("Simba");
        Creature tiger = new Tiger("Shere Khan");
        
        System.out.println(dog.getName() + ": ");
        dog.makeSound();
        
        System.out.println("\n" + cat.getName() + ": ");
        cat.makeSound();
        
        System.out.println("\n" + lion.getName() + ": ");
        lion.makeSound();
        
        System.out.println("\n" + tiger.getName() + ": ");
        tiger.makeSound();
        
        System.out.println("\n✓ WildCreature itself is sealed - it limits to Lion and Tiger");
        System.out.println("✓ WildCreature extends Creature");
    }
    
    private static void demonstrateTypeChecking() {
        System.out.println("\n\nEXAMPLE 3: Type Checking with Sealed Classes");
        System.out.println("───────────────────────────────────────────────\n");
        
        Vehicle vehicle = new Car("Blue", 100, 2);
        
        // Compiler knows these are the only possibilities
        String vehicleType = checkVehicleType(vehicle);
        System.out.println("Vehicle type: " + vehicleType);
        
        // With sealed classes, compiler can detect exhaustive switch
        analyzeVehicle(new Car("Green", 110, 4));
        analyzeVehicle(new Motorcycle("Yellow", 160, true));
        analyzeVehicle(new Truck("Brown", 90, 60));
    }
    
    private static String checkVehicleType(Vehicle v) {
        // Compiler knows these are the ONLY possible types
        if (v instanceof Car) {
            return "It's a Car";
        } else if (v instanceof Motorcycle) {
            return "It's a Motorcycle";
        } else if (v instanceof Truck) {
            return "It's a Truck";
        } else {
            return "Unknown vehicle";
        }
    }
    
    private static void analyzeVehicle(Vehicle v) {
        // Sealed classes enable exhaustive pattern matching
        String analysis = switch(v) {
            case Car car -> "Car with " + getCarInfo(car) + " doors";
            case Motorcycle bike -> "Motorcycle: " + getMotoInfo(bike);
            case Truck truck -> "Truck with capacity: " + getTruckInfo(truck) + " tons";
        };
        
        System.out.println("→ " + analysis);
    }
    
    private static int getCarInfo(Car car) {
        // Cannot access private field directly in demo, would use accessor in real code
        return 4; // Example value
    }
    
    private static boolean getMotoInfo(Motorcycle bike) {
        return true;
    }
    
    private static double getTruckInfo(Truck truck) {
        return 50.0;
    }
}


/*

════════════════════════════════════════════════════════════════════════════
SEALED CLASSES - INTERVIEW Q&A
════════════════════════════════════════════════════════════════════════════

Q1: What are sealed classes?
────────────────────────────
A: Sealed classes allow you to restrict which classes can extend (subclass) 
   a sealed class. You use the 'sealed' keyword and 'permits' clause to specify
   exactly which classes are allowed to extend the sealed class.

   public sealed class Shape permits Circle, Rectangle { }
   // Only Circle and Rectangle can extend Shape


Q2: Why would you use sealed classes?
──────────────────────────────────────
A: Benefits:
   ✓ Type safety - Restrict inheritance hierarchy
   ✓ Better optimization - JVM can make better assumptions
   ✓ Clearer intent - Shows design constraints
   ✓ Exhaustive pattern matching - Compiler can warn if not all cases covered
   ✓ Maintainability - Easier to understand all subclasses


Q3: What modifiers can a sealed class have?
────────────────────────────────────────────
A: Permitted subclasses can have three modifiers:
   
   1. final - Cannot be extended further
      final class Car extends Vehicle { }
   
   2. sealed - Can be extended by specific subclasses
      sealed class WildAnimal extends Animal permits Lion, Tiger { }
   
   3. non-sealed - Can be extended by any class
      non-sealed class AbstractVehicle extends Vehicle { }


Q4: Can a sealed class be abstract?
────────────────────────────────────
A: Yes! A sealed class can be abstract and have abstract methods.
   
   public sealed abstract class Shape permits Circle, Rectangle { }
   
   This is actually very common - the sealed class provides the contract,
   and subclasses implement it.


Q5: What's the difference between sealed and final?
─────────────────────────────────────────────────────
A: 
   • final class:       Cannot be extended AT ALL
   • sealed class:      Can be extended ONLY by permitted classes
   
   Example:
   final class X { }           // X cannot be extended by anyone
   sealed class Y permits A { }  // Y can be extended only by A


Q6: Can sealed classes have fields?
────────────────────────────────────
A: Yes! Sealed classes can have fields, methods, and constructors just like
   any regular class. The sealed modifier only restricts inheritance.
   
   public sealed class Vehicle permits Car {
       private String color;    // ✓ Can have fields
       private int speed;       // ✓ Can have fields
       
       public void drive() { }  // ✓ Can have methods
   }


Q7: What happens if you forget to mark a subclass?
────────────────────────────────────────────────────
A: Compile error! The subclass must be declared with one of:
   • final
   • sealed
   • non-sealed
   
   If you don't specify, compilation fails:
   
   public sealed class Shape permits Circle { }
   public class Circle extends Shape { }  // ✗ ERROR: Must be final/sealed/non-sealed


Q8: Can sealed interfaces exist?
──────────────────────────────────
A: Yes! Java 17 also supports sealed interfaces.
   
   public sealed interface Animal permits Dog, Cat { }
   
   Only classes implementing the permitted types can implement this interface.


Q9: What is exhaustive switch with sealed classes?
───────────────────────────────────────────────────
A: When you use a sealed class in a switch, the compiler can verify that
   you've covered all possible types.
   
   Shape shape = new Circle();
   switch(shape) {
       case Circle c -> { }
       case Rectangle r -> { }
       // No need for 'default' - compiler knows these are all cases
   }


Q10: Can sealed classes participate in generics?
──────────────────────────────────────────────────
A: Yes! Sealed classes work well with generics.
   
   public sealed class Container<T> permits StringContainer {
       private T value;
   }


════════════════════════════════════════════════════════════════════════════

*/
