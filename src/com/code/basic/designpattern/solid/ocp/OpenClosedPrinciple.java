package com.code.basic.designpattern.solid.ocp;

/**
 * Open-Closed Principle (OCP)
 * Software entities should be open for extension but closed for modification.
 * You should be able to add new features without changing existing code.
 */

// BAD EXAMPLE - Violates OCP
class DiscountCalculatorBad {
    public double calculateDiscount(String customerType, double amount) {
        if (customerType.equals("REGULAR")) {
            return amount * 0.05; // 5% discount
        } else if (customerType.equals("PREMIUM")) {
            return amount * 0.10; // 10% discount
        } else if (customerType.equals("VIP")) {
            return amount * 0.20; // 20% discount
        }
        // To add a new customer type, we have to modify this class
        return 0;
    }
}

// GOOD EXAMPLE - Follows OCP
interface DiscountStrategy {
    double calculateDiscount(double amount);
}

class RegularCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.05; // 5% discount
    }
}

class PremiumCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.10; // 10% discount
    }
}

class VIPCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.20; // 20% discount
    }
}

// New customer type - NO modification to existing code
class GoldCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.15; // 15% discount
    }
}

class DiscountCalculator {
    private DiscountStrategy discountStrategy;

    public DiscountCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculateDiscount(double amount) {
        return discountStrategy.calculateDiscount(amount);
    }
}

// Demo
public class OpenClosedPrinciple {
    public static void main(String[] args) {
        double amount = 1000;

        // Regular customer
        DiscountCalculator regularCalc = new DiscountCalculator(new RegularCustomerDiscount());
        System.out.println("Regular Customer Discount: " + regularCalc.calculateDiscount(amount));

        // Premium customer
        DiscountCalculator premiumCalc = new DiscountCalculator(new PremiumCustomerDiscount());
        System.out.println("Premium Customer Discount: " + premiumCalc.calculateDiscount(amount));

        // VIP customer
        DiscountCalculator vipCalc = new DiscountCalculator(new VIPCustomerDiscount());
        System.out.println("VIP Customer Discount: " + vipCalc.calculateDiscount(amount));

        // Gold customer - NEW type without modifying existing code
        DiscountCalculator goldCalc = new DiscountCalculator(new GoldCustomerDiscount());
        System.out.println("Gold Customer Discount: " + goldCalc.calculateDiscount(amount));

        System.out.println("\nBenefit: Open for extension (new customer types), Closed for modification (no existing code changed)");
    }
}
