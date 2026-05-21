package com.code.basic.designpattern.strategy;

/**
 * Context Class: ShoppingCart
 * 
 * The context maintains a reference to a PaymentStrategy object
 * and delegates payment operations to it.
 * The context doesn't care which payment method is used.
 */
class ShoppingCart {
    private String orderId;
    private double totalAmount;
    private PaymentStrategy paymentStrategy;
    private java.util.List<String> items;

    public ShoppingCart(String orderId) {
        this.orderId = orderId;
        this.items = new java.util.ArrayList<>();
        this.totalAmount = 0;
    }

    /**
     * Set the payment strategy (can be changed anytime)
     */
    public void setPaymentStrategy(PaymentStrategy strategy) {
        System.out.println("\n🛒 Payment method changed to: " + strategy.getPaymentMethodName());
        this.paymentStrategy = strategy;
    }

    /**
     * Add item to cart
     */
    public void addItem(String itemName, double price) {
        items.add(itemName + " - ₹" + price);
        totalAmount += price;
    }

    /**
     * Display cart contents
     */
    public void displayCart() {
        System.out.println("\n📦 Order ID: " + orderId);
        System.out.println("Items in Cart:");
        for (String item : items) {
            System.out.println("   • " + item);
        }
        System.out.println("Total Amount: ₹" + totalAmount);
    }

    /**
     * Checkout - uses the current payment strategy
     */
    public void checkout() {
        if (paymentStrategy == null) {
            System.out.println("❌ No payment strategy selected!");
            return;
        }

        System.out.println("\n💰 Starting Checkout...");
        System.out.println("Using: " + paymentStrategy.getPaymentMethodName());

        // Validate payment method
        if (!paymentStrategy.validateCredentials()) {
            System.out.println("❌ Payment credentials validation failed!");
            return;
        }

        // Process payment
        paymentStrategy.processPayment(totalAmount);
    }

    /**
     * Request refund using current payment method
     */
    public void requestRefund(double amount) {
        if (paymentStrategy == null) {
            System.out.println("❌ No payment method available for refund!");
            return;
        }

        System.out.println("\n🔄 Processing Refund...");
        paymentStrategy.refund(amount);
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
