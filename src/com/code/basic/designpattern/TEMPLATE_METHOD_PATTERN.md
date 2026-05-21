# Template Method Design Pattern - Complete Guide

## 🎯 What is Template Method Pattern?

**In Simple Words:**
Template Method Pattern defines the skeleton of an algorithm in a base class but lets subclasses override specific steps without changing the algorithm's structure.

Think of it like:
- A recipe for baking a cake
- The recipe structure is fixed: mix ingredients → bake → cool → decorate
- But different cakes skip or modify certain steps (chocolate vs vanilla)
- The overall process structure remains the same

## 📋 Real-World Analogy: Coffee & Tea Preparation

Both coffee and tea follow similar preparation steps:
1. Boil water
2. Add ingredient (coffee grounds or tea leaves)
3. Pour water into cup
4. Add toppings (sugar, milk, etc.)

The structure is identical, but specific steps differ!

```
Template (preparation process)
         ↓
├─ Coffee → Different ingredients
├─ Tea → Different brewing time
├─ Hot Chocolate → Different toppings
└─ All follow same structure
```

## 🏗️ Structure

```
┌─────────────────────────────────┐
│    AbstractClass (Template)     │
├─────────────────────────────────┤
│ + templateMethod() [FINAL]      │
│   1. step1()                    │
│   2. step2()                    │
│   3. step3()                    │
│                                 │
│ # step1()        [ABSTRACT]     │
│ # step2()        [ABSTRACT]     │
│ # step3()        [CONCRETE]     │
└────────────┬────────────────────┘
             △
    ┌────────┴────────────┐
    │                     │
┌───┴────────┐    ┌──────┴───┐
│ConcreteA   │    │ConcreteB │
├────────────┤    ├──────────┤
│step1()     │    │step1()   │
│step2()     │    │step2()   │
│step3(): OK │    │step3()   │
└────────────┘    └──────────┘
```

## 🔑 Key Components

### 1. **Abstract Class (Template)**
- Defines the template method (usually final/sealed)
- Contains algorithm structure
- Has abstract methods for customizable steps
- May have concrete methods for common steps

### 2. **Concrete Classes (Implementations)**
- Implement abstract methods
- Provide specific behavior for each step
- Don't override the template method

### 3. **Template Method**
- Usually marked as `final` (can't be overridden)
- Defines the order of steps
- Controls the algorithm flow
- Calls abstract and concrete methods

## 💡 How It Works

### Step 1: Define Template in Abstract Class
```
AbstractBeverage
├─ templateMethod() - FINAL
│  1. boilWater()
│  2. addIngredient()
│  3. pour()
│  4. addToppings()
│
├─ boilWater() - CONCRETE (same for all)
├─ addIngredient() - ABSTRACT (varies)
├─ pour() - CONCRETE (same for all)
└─ addToppings() - ABSTRACT (varies)
```

### Step 2: Create Concrete Classes
```
Coffee extends AbstractBeverage
├─ addIngredient() → Add coffee grounds
└─ addToppings() → Add milk & sugar

Tea extends AbstractBeverage
├─ addIngredient() → Add tea leaves
└─ addToppings() → Add honey & lemon
```

### Step 3: Use It
```
Beverage coffee = new Coffee();
coffee.templateMethod();  // Uses template structure

Beverage tea = new Tea();
tea.templateMethod();  // Same structure, different steps
```

## 🆚 Key Differences from Other Patterns

| Pattern | Purpose | Control |
|---------|---------|---------|
| **Template Method** | Define algorithm structure | Base class controls flow |
| **Strategy** | Change algorithm choice | Client chooses strategy |
| **Factory** | Create objects | Factory creates object |
| **Decorator** | Add behavior | Runtime wrapping |
| **State** | Change behavior by state | State dictates behavior |

## ✨ Benefits

### 1. **Code Reusability**
- Common logic in base class
- Avoid code duplication
- Share structure across subclasses

### 2. **Flexibility**
- Subclasses customize specific steps
- Don't need to rewrite entire algorithm
- Natural extension point

### 3. **Enforced Structure**
- Algorithm steps guaranteed
- No jumping around or skipping steps
- Consistent execution flow

### 4. **Open/Closed Principle**
- Open for extension (override specific methods)
- Closed for modification (don't change template)

### 5. **Inversion of Control**
- Base class calls subclass methods
- Also known as "Hollywood Principle"
- "Don't call us, we'll call you"

### 6. **Consistency**
- All implementations follow same structure
- Easier to understand and maintain
- Predictable behavior

## 📊 Real-World Examples

### 1. **Document Processing**
```
AbstractDocument
├─ process() [TEMPLATE]
│  ├─ parseDocument()
│  ├─ validateData()
│  ├─ transformData()
│  └─ saveDocument()
│
├─ PDFDocument
├─ WordDocument
├─ ExcelDocument
└─ XMLDocument
```

### 2. **Game Development**
```
AbstractGame
├─ play() [TEMPLATE]
│  ├─ initialize()
│  ├─ startMenu()
│  ├─ runGame()
│  └─ endGame()
│
├─ ChessGame
├─ PokerGame
├─ SnakeGame
└─ PacmanGame
```

### 3. **Data Processing Pipeline**
```
AbstractDataProcessor
├─ process() [TEMPLATE]
│  ├─ readData()
│  ├─ validate()
│  ├─ transform()
│  └─ output()
│
├─ CSVProcessor
├─ JSONProcessor
├─ XMLProcessor
└─ DatabaseProcessor
```

### 4. **Software Installation**
```
AbstractInstaller
├─ install() [TEMPLATE]
│  ├─ downloadFiles()
│  ├─ checkDependencies()
│  ├─ installFiles()
│  ├─ configure()
│  └─ cleanup()
│
├─ WindowsInstaller
├─ LinuxInstaller
├─ MacInstaller
└─ DockerInstaller
```

### 5. **Cooking Recipes**
```
AbstractRecipe
├─ cook() [TEMPLATE]
│  ├─ gatherIngredients()
│  ├─ prepareIngredients()
│  ├─ cookSteps()
│  ├─ plate()
│  └─ serve()
│
├─ PizzaRecipe
├─ PastaRecipe
├─ SushiRecipe
└─ CakeRecipe
```

### 6. **Framework Lifecycle (Most Common)**
```
AbstractServlet (Spring Boot)
├─ init() [TEMPLATE - FINAL]
│  ├─ validateConfig()
│  ├─ loadResources()
│  ├─ setupServices()
│  └─ startServer()
│
└─ Your Servlet (override specific methods)

AbstractTestCase (JUnit)
├─ runTest() [TEMPLATE - FINAL]
│  ├─ setUp()
│  ├─ executeTest()
│  └─ tearDown()
│
└─ Your Test (override setUp, test, tearDown)
```

## 🎯 When to Use

### ✅ Use Template Method Pattern when:

1. **Multiple classes have same algorithm structure**
   - But vary in specific steps
   - Example: Different document formats

2. **Code duplication in subclasses**
   - Common logic should be in base class
   - Only differences in subclasses

3. **You want to control subclass behavior**
   - Enforce algorithm structure
   - Allow customization at specific points

4. **Inversion of Control needed**
   - Base class calls subclass methods
   - Framework-like behavior

5. **Variations of same process**
   - Same workflow, different implementations
   - Example: Payment processing by different gateways

### ❌ Don't use when:

- Only one or two classes involved
- Algorithm steps are completely different
- Subclasses need complete autonomy
- Complexity not justified by reuse
- Simple inheritance hierarchy is better

## 🔗 Related Patterns

| Pattern | Relationship |
|---------|--------------|
| **Strategy** | Alternate to template method; client chooses algorithm |
| **Factory** | Often used together; template method creates objects |
| **State** | Similar structure; state dictates method calls |
| **Iterator** | Iterates through elements using template method |
| **Observer** | Can use template method for notification flow |

## 📋 Implementation Checklist

### Design Phase
- [ ] Identify algorithm structure
- [ ] Find common steps
- [ ] Find varying steps
- [ ] Define abstract class
- [ ] Create abstract methods for variations

### Coding Phase
- [ ] Write abstract class with template method (FINAL)
- [ ] Mark template method as final/sealed
- [ ] Implement concrete methods in abstract class
- [ ] Define abstract methods for subclasses
- [ ] Create concrete classes
- [ ] Implement abstract methods
- [ ] Add hooks for extensions (optional)

### Testing Phase
- [ ] Test each concrete implementation
- [ ] Verify template method not overridden
- [ ] Test algorithm structure integrity
- [ ] Verify inheritance hierarchy

## 💻 Pseudo-Code Example

```
Abstract class: PaymentProcessor
├─ final processPayment(amount)
│  1. validatePaymentDetails()      [ABSTRACT]
│  2. calculateFees()               [CONCRETE]
│  3. deductAmount()                [ABSTRACT]
│  4. recordTransaction()           [CONCRETE]
│  5. sendConfirmation()            [ABSTRACT]
│
└─ Methods:
   ├─ calculateFees() → 2% for all
   ├─ recordTransaction() → Same logging for all
   ├─ validatePaymentDetails() → Different per processor
   ├─ deductAmount() → Different per processor
   └─ sendConfirmation() → Different per processor


Class: CreditCardProcessor extends PaymentProcessor
├─ validatePaymentDetails() → Check card validity
├─ deductAmount() → Deduct from card
└─ sendConfirmation() → Send email

Class: PayPalProcessor extends PaymentProcessor
├─ validatePaymentDetails() → Check PayPal account
├─ deductAmount() → Deduct from PayPal wallet
└─ sendConfirmation() → Send SMS

Class: CryptoProcessor extends PaymentProcessor
├─ validatePaymentDetails() → Verify wallet
├─ deductAmount() → Transfer crypto
└─ sendConfirmation() → Blockchain notification
```

## 🏆 Pro Tips

### 1. **Use Hooks for Optional Customization**
- Add `protected` hook methods
- Subclasses can override if needed
- Provides flexibility without breaking structure

### 2. **Make Template Method Final**
- Prevents subclasses from changing flow
- Ensures algorithm structure integrity
- "Hollywood Principle" enforcement

### 3. **Keep Template Simple**
- Don't make template method too long
- Extract complex logic into separate methods
- Easier to understand and maintain

### 4. **Document the Template**
- Explain algorithm flow clearly
- Document which methods must/can be overridden
- Provide examples for subclasses

### 5. **Use Meaningful Names**
- Method names should describe their purpose
- Example: `boilWater()` vs `prepareWater()`
- Makes template structure self-documenting

### 6. **Combine with Factory Pattern**
- Template method for process flow
- Factory for object creation
- Powerful combination for frameworks

## 🎓 Learning Resources

### Concepts to Master:
- [ ] Abstract classes and methods
- [ ] Method overriding
- [ ] Inheritance hierarchy
- [ ] Protected access modifier
- [ ] Polymorphism

### Common Misconceptions:
1. **NOT** about code reuse alone (that's just a benefit)
2. **NOT** about inheritance hierarchy (inheritance is tool, not goal)
3. **NOT** same as Strategy pattern (strategy changes algorithm, template defines structure)
4. **NOT** just putting common code in base class

## 🎯 Interview Questions

1. **What is Template Method Pattern?**
   - Answer: Defines algorithm structure in base class, lets subclasses override specific steps

2. **Difference from Strategy Pattern?**
   - Template: Base class controls flow (inheritance)
   - Strategy: Client controls algorithm choice (composition)

3. **Why make template method final?**
   - Prevents subclasses from changing algorithm structure
   - Ensures consistency across implementations

4. **Real-world examples?**
   - Frameworks (Spring, JUnit)
   - Document processing
   - Game development
   - Installation wizards

5. **Benefits over simple inheritance?**
   - Enforces algorithm structure
   - Clear extension points
   - Prevents breaking flow
   - "Hollywood Principle"

## 🔍 Code Analysis

### Pattern Indicators (Look for these):
- ✓ Abstract base class with concrete and abstract methods
- ✓ Template method marked as final
- ✓ Similar algorithm flow in different classes
- ✓ Same steps, different implementations
- ✓ Framework lifecycle methods

### Anti-Patterns (Avoid these):
- ✗ Overriding template method in subclasses
- ✗ Completely different implementations ignoring template
- ✗ Template method too long/complex
- ✗ Forcing unrelated classes to use template
- ✗ No clear separation of common/varying steps

## 📚 Template Method in Popular Frameworks

### Spring Boot
```
DispatcherServlet
├─ doService() [TEMPLATE]
└─ Your controller methods override specific handling
```

### JUnit
```
TestCase
├─ run() [TEMPLATE]
│  ├─ setUp()
│  ├─ runTest()
│  └─ tearDown()
└─ Your test class overrides these methods
```

### Android
```
Activity
├─ onCreate() [HOOK]
├─ onStart() [HOOK]
├─ onResume() [HOOK]
└─ Your Activity overrides lifecycle methods
```

### JDBC
```
JdbcTemplate
├─ query() [TEMPLATE]
├─ execute() [TEMPLATE]
└─ You provide callbacks without knowing internals
```

## 🎓 Main Takeaway

**"Template Method Pattern = Define algorithm skeleton in base class, let subclasses fill in the details"**

The base class says: "Here's the process. You complete these steps your way, but follow the structure."

Perfect for:
- Frameworks and libraries
- Algorithms with variations
- Ensuring consistent workflow
- Reducing code duplication
- Creating extension points

---

## Template Method Pattern Summary Card

| Aspect | Details |
|--------|---------|
| **Type** | Behavioral Pattern |
| **Use When** | Algorithm structure same, steps vary |
| **Key Principle** | Hollywood Principle (Don't call us, we'll call you) |
| **Main Benefit** | Enforce algorithm structure + Enable customization |
| **Related Patterns** | Strategy, Factory, State |
| **Complexity** | Low to Medium |
| **Reusability** | High |
| **Testability** | High |
| **Common Usage** | Frameworks, Workflows, Pipelines |

