package com.code.basic.designpattern.strategy;

/**
 * STRATEGY PATTERN DEMO - Payment Processing System
 * 
 * This demo shows:
 * 1. How to use different payment strategies
 * 2. How to switch between strategies
 * 3. How the context (ShoppingCart) doesn't care about the strategy
 * 4. Real-world payment scenarios
 */
public class StrategyPatternPaymentDemo {

    public static void main(String[] args) {
        System.out.println("====== STRATEGY PATTERN - PAYMENT SYSTEM DEMO ======\n");

        // Example 1: Credit Card Payment
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 1: Customer pays with Credit Card");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateCreditCardPayment();

        // Example 2: PayPal Payment
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 2: Customer pays with PayPal");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstratePayPalPayment();

        // Example 3: Google Pay Payment
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 3: Customer pays with Google Pay");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateGooglePayPayment();

        // Example 4: Apple Pay Payment
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 4: Customer pays with Apple Pay");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateApplePayPayment();

        // Example 5: Cryptocurrency Payment
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 5: Customer pays with Bitcoin");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateCryptoPayment();

        // Example 6: Dynamic Strategy Switching
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 6: Strategy Switching (Customer hesitates)");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateStratagySwitching();

        // Example 7: Key Benefits
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("WHY STRATEGY PATTERN IS USEFUL?");
        System.out.println("═══════════════════════════════════════════════════════");
        demonstrateBenefits();

        // Example 8: Using Stripe Payment
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("SCENARIO 8: Customer pays with Stripe");
        System.out.println("═══════════════════════════════════════════════════════");
        useStripePayment();
    }

    private static void demonstrateCreditCardPayment() {
        ShoppingCart cart = new ShoppingCart("ORD-2024-001");

        // Add items
        cart.addItem("Laptop", 45000);
        cart.addItem("Mouse", 1200);
        cart.addItem("Keyboard", 3500);

        cart.displayCart();

        // Create and set credit card strategy
        PaymentStrategy creditCard = new CreditCardPayment(
                "4532123456789010",
                "John Doe",
                "12/26",
                "123"
        );
        cart.setPaymentStrategy(creditCard);

        // Checkout
        cart.checkout();
    }

    private static void demonstratePayPalPayment() {
        ShoppingCart cart = new ShoppingCart("ORD-2024-002");

        // Add items
        cart.addItem("Phone", 25000);
        cart.addItem("Phone Case", 800);
        cart.addItem("Screen Protector", 400);

        cart.displayCart();

        // Create and set PayPal strategy
        PaymentStrategy paypal = new PayPalPayment(
                "john.doe@gmail.com",
                "SecurePassword123"
        );
        cart.setPaymentStrategy(paypal);

        // Checkout
        cart.checkout();
    }

    private static void demonstrateGooglePayPayment() {
        ShoppingCart cart = new ShoppingCart("ORD-2024-003");

        // Add items
        cart.addItem("Headphones", 5000);
        cart.addItem("Charging Cable", 500);

        cart.displayCart();

        // Create and set Google Pay strategy
        PaymentStrategy googlePay = new GooglePayPayment(
                "9876543210",
                "johndoe@upi"
        );
        cart.setPaymentStrategy(googlePay);

        // Checkout
        cart.checkout();
    }

    private static void demonstrateApplePayPayment() {
        ShoppingCart cart = new ShoppingCart("ORD-2024-004");

        // Add items
        cart.addItem("AirPods", 8000);
        cart.addItem("Lightning Cable", 1200);

        cart.displayCart();

        // Create and set Apple Pay strategy
        PaymentStrategy applePay = new ApplePayPayment(
                "IPHX1234ABCD5678",
                "tokenized_secure_data_xyz"
        );
        cart.setPaymentStrategy(applePay);

        // Checkout
        cart.checkout();
    }

    private static void demonstrateCryptoPayment() {
        ShoppingCart cart = new ShoppingCart("ORD-2024-005");

        // Add items
        cart.addItem("Bitcoin Hardware Wallet", 8000);
        cart.addItem("Crypto Guide Book", 500);

        cart.displayCart();

        // Create and set Crypto strategy
        PaymentStrategy crypto = new CryptoPayment(
                "1A1z7agoat4WFhN3x3CwYrBpa5qxqjHS5C",
                0.015
        );
        cart.setPaymentStrategy(crypto);

        // Checkout
        cart.checkout();
    }

    private static void demonstrateStratagySwitching() {
        ShoppingCart cart = new ShoppingCart("ORD-2024-006");

        // Add items
        cart.addItem("Book", 500);
        cart.addItem("Notebook", 200);

        cart.displayCart();

        // Customer first tries Credit Card
        System.out.println("\n💭 Customer: Let me try Credit Card...");
        PaymentStrategy creditCard = new CreditCardPayment(
                "1111222233334444",
                "Jane Smith",
                "03/25",
                "456"
        );
        cart.setPaymentStrategy(creditCard);
        cart.checkout();

        // Credit card fails (insufficient balance in this demo)
        // Customer changes mind and uses PayPal instead
        System.out.println("\n💭 Customer: Oh! Let me use PayPal instead...");
        PaymentStrategy paypal = new PayPalPayment(
                "jane.smith@yahoo.com",
                "MySecurePass456"
        );
        cart.setPaymentStrategy(paypal);
        cart.checkout();

        // Demonstrate refund
        System.out.println("\n💭 Customer: I want to return this order!");
        cart.requestRefund(700);
    }

    private static void useStripePayment(){
        ShoppingCart cart = new ShoppingCart("ORD-2024-007");

        // Add items
        cart.addItem("Gaming Console", 30000);
        cart.addItem("Game Controller", 5000);

        cart.displayCart();

        // Create and set Stripe strategy
        PaymentStrategy stripe = new StripePayment(
                "stripe.user@example.com",
                "sk_test_default12345"
        );
        cart.setPaymentStrategy(stripe);

        // Checkout
        cart.checkout();
    }

    private static void demonstrateBenefits() {
        System.out.println("\n✨ KEY BENEFITS OF STRATEGY PATTERN:\n");

        System.out.println("1. 🔄 INTERCHANGEABILITY");
        System.out.println("   - Payment methods can be swapped at runtime");
        System.out.println("   - No code changes needed to add new payment method\n");

        System.out.println("2. 📦 ENCAPSULATION");
        System.out.println("   - Each payment strategy encapsulates its own logic");
        System.out.println("   - ShoppingCart doesn't know implementation details\n");

        System.out.println("3. 🧩 OPEN/CLOSED PRINCIPLE");
        System.out.println("   - Open for extension (add new payment methods)");
        System.out.println("   - Closed for modification (don't change existing code)\n");

        System.out.println("4. 🎯 SINGLE RESPONSIBILITY");
        System.out.println("   - Each strategy handles one payment method");
        System.out.println("   - Easy to test and maintain\n");

        System.out.println("5. ⚡ RUNTIME SELECTION");
        System.out.println("   - Strategy can be chosen at runtime");
        System.out.println("   - Customer can switch payment method during checkout\n");

        System.out.println("6. 🔧 EASY TO EXTEND");
        System.out.println("   - Adding new payment method:");
        System.out.println("   - Just create new class implementing PaymentStrategy");
        System.out.println("   - No impact on existing code!\n");

        System.out.println("7. 🧪 HIGHLY TESTABLE");
        System.out.println("   - Each strategy can be tested independently");
        System.out.println("   - Mock strategies for testing ShoppingCart logic\n");
    }
}
