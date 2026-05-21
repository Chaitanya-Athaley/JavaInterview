# Strategy Design Pattern - Payment System Example

## 🎯 What is Strategy Pattern?

**In Simple Words:**
Strategy Pattern lets you **swap algorithms at runtime** without changing the code that uses them.

Think of it like:
- Going to a restaurant and choosing **different payment methods** (Cash, Card, UPI)
- The bill amount stays same, only **how you pay** changes
- The cashier doesn't care which method you use

## 💳 Real-World Analogy: E-commerce Checkout

```
Customer adds items to cart
         ↓
Proceeds to checkout
         ↓
Chooses payment method:
  ├─ Credit Card
  ├─ PayPal
  ├─ Google Pay
  ├─ Apple Pay
  └─ Bitcoin
         ↓
Payment processed using chosen method
         ↓
Order complete
```

**Key Point:** The checkout process is identical, only the **payment algorithm** changes!

## 🏗️ Structure

```
┌──────────────────────────┐
│   ShoppingCart           │
│   (Context)              │
│                          │
│  - paymentStrategy       │
│  - checkout()            │
│  - setPaymentStrategy()  │
└──────────────────────────┘
           △
           │ uses
           │
    ┌──────┴──────────────────────────┐
    │                                  │
    │ PaymentStrategy (Interface)      │
    │ ─────────────────────────────    │
    │ + validateCredentials()          │
    │ + processPayment()               │
    │ + refund()                       │
    │ + getPaymentMethodName()         │
    └──────────────────────────────────┘
           △           △          △           △            △
           │           │          │           │            │
    ┌──────┴──┐ ┌──────┴──┐ ┌────┴──┐ ┌─────┴───┐ ┌──────┴──┐
    │          │ │         │ │       │ │         │ │         │
  CreditCard PayPal  GooglePay ApplePay Crypto
   Payment   Payment Payment   Payment  Payment
```

## 📁 Files Description

| File | Purpose |
|------|---------|
| `PaymentStrategies.java` | All payment strategy implementations |
| `ShoppingCart.java` | Context class that uses strategies |
| `StrategyPatternPaymentDemo.java` | Demo showing all scenarios |

## 🔑 Key Components

### 1. Strategy Interface
```java
interface PaymentStrategy {
    boolean validateCredentials();
    void processPayment(double amount);
    void refund(double amount);
    String getPaymentMethodName();
}
```

### 2. Concrete Strategies
```java
class CreditCardPayment implements PaymentStrategy { ... }
class PayPalPayment implements PaymentStrategy { ... }
class GooglePayPayment implements PaymentStrategy { ... }
class ApplePayPayment implements PaymentStrategy { ... }
class CryptoPayment implements PaymentStrategy { ... }
```

### 3. Context
```java
class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    
    public void checkout() {
        paymentStrategy.validateCredentials();
        paymentStrategy.processPayment(totalAmount);
    }
}
```

## 💡 How It Works

### Step 1: Create Strategies
```java
PaymentStrategy creditCard = new CreditCardPayment(...);
PaymentStrategy paypal = new PayPalPayment(...);
PaymentStrategy googlePay = new GooglePayPayment(...);
```

### Step 2: Set Strategy in Context
```java
ShoppingCart cart = new ShoppingCart("ORD-001");
cart.addItem("Laptop", 45000);
cart.setPaymentStrategy(creditCard);  // Use credit card
```

### Step 3: Use Strategy
```java
cart.checkout();  // Internally uses credit card payment
```

### Step 4: Switch Strategy (Optional)
```java
cart.setPaymentStrategy(paypal);  // Change to PayPal
cart.checkout();  // Now uses PayPal
```

## ✨ Benefits

### 1. **Runtime Flexibility**
```java
// Can switch strategy anytime
cart.setPaymentStrategy(creditCard);
cart.checkout();

cart.setPaymentStrategy(paypal);
cart.checkout();  // Different strategy, same code
```

### 2. **Easy to Add New Strategies**
```java
// Add new payment method without modifying existing code
class StripePayment implements PaymentStrategy {
    // Implement interface
}

// Just use it!
cart.setPaymentStrategy(new StripePayment(...));
```

### 3. **Encapsulation**
- Each strategy hides its implementation
- Cart doesn't know HOW credit card works
- Only knows that it implements PaymentStrategy

### 4. **Open/Closed Principle**
- Open for extension (add new payment methods)
- Closed for modification (don't change existing code)

### 5. **Single Responsibility**
- CreditCardPayment only handles card logic
- PayPalPayment only handles PayPal logic
- ShoppingCart only handles checkout logic

### 6. **Testing**
```java
// Easy to test with mock strategies
class MockPaymentStrategy implements PaymentStrategy {
    // Fake implementation for testing
}

cart.setPaymentStrategy(new MockPaymentStrategy());
```

## 🆚 Strategy vs Factory Pattern

| Aspect | Strategy | Factory |
|--------|----------|---------|
| **Purpose** | Change algorithm behavior | Create objects |
| **When** | Runtime behavior change | Object creation time |
| **Example** | Payment method selection | Create vehicle type |
| **Focus** | HOW to do something | WHAT object to create |

## 📊 Comparison: With vs Without Pattern

### ❌ WITHOUT Strategy Pattern
```java
// Bad: Lots of if-else
if (paymentMethod.equals("CREDIT_CARD")) {
    validateCreditCard();
    processCreditCard(amount);
} else if (paymentMethod.equals("PAYPAL")) {
    validatePayPal();
    processPayPal(amount);
} else if (paymentMethod.equals("GOOGLE_PAY")) {
    validateGooglePay();
    processGooglePay(amount);
}
// Adding new method = modify this code every time!
```

### ✅ WITH Strategy Pattern
```java
// Clean: Just set strategy and use it
cart.setPaymentStrategy(new PayPalPayment(...));
cart.checkout();  // Simple!
// Adding new method = just create new class!
```

## 🎯 When to Use

✅ Use Strategy Pattern when:
- You have multiple algorithms for a task
- You want to switch algorithms at runtime
- You want to avoid if-else statements
- Each algorithm needs different implementation
- Algorithm choice depends on runtime data

❌ Don't use if:
- Only one simple algorithm
- Algorithm selection doesn't change
- Complexity isn't justified

## 🔗 Related Patterns

- **State Pattern**: Behavior changes with object state
- **Template Method**: Algorithm structure in base class
- **Decorator Pattern**: Add behavior to objects dynamically

## 🧪 Running the Example

```bash
# Compile
javac PaymentStrategies.java ShoppingCart.java StrategyPatternPaymentDemo.java

# Run
java StrategyPatternPaymentDemo
```

## 📊 Example Output

```
====== STRATEGY PATTERN - PAYMENT SYSTEM DEMO ======

═══════════════════════════════════════════════════════
SCENARIO 1: Customer pays with Credit Card
═══════════════════════════════════════════════════════

📦 Order ID: ORD-2024-001
Items in Cart:
   • Laptop - ₹45000.0
   • Mouse - ₹1200.0
   • Keyboard - ₹3500.0
Total Amount: 49700.0

💰 Starting Checkout...
Using: Credit Card (****-****-****-9010)

💳 Validating Credit Card...
   Card Number: ****-****-****-9010
   Expiry: 12/26
   CVV: Valid
✅ Payment Successful!
```

## 🎓 Interview Tips

1. **Explain the pattern clearly**: "Strategy lets you choose algorithm at runtime"
2. **Use payment example**: Easy to understand and relatable
3. **Show code structure**: Interface + implementations + context
4. **Demonstrate flexibility**: Show how strategies can be switched
5. **Compare with alternatives**: What if we used if-else instead?
6. **Real-world examples**: Payment, sorting, compression, etc.

## 💻 Adding New Payment Method (Easy!)

```java
class StripePayment implements PaymentStrategy {
    @Override
    public boolean validateCredentials() { ... }
    
    @Override
    public void processPayment(double amount) { ... }
    
    @Override
    public void refund(double amount) { ... }
    
    @Override
    public String getPaymentMethodName() { ... }
}

// Use it
cart.setPaymentStrategy(new StripePayment(...));
cart.checkout();
```

**No changes needed in ShoppingCart or other classes!** ✨

## 🎓 Main Takeaway

**"Strategy Pattern = Swap algorithms without changing client code"**

Perfect for e-commerce, payment systems, sorting algorithms, compression methods, and any scenario where multiple algorithms can do the same job!
