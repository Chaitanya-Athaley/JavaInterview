package com.code.basic.designpattern.strategy;

public class StripePayment implements PaymentStrategy{

    private String accountEmail;
    private String apiKey;
    private double stripeBalance;

    public StripePayment() {
        this("stripe.user@example.com", "sk_test_default12345", 100000);
    }

    public StripePayment(String accountEmail, String apiKey) {
        this(accountEmail, apiKey, 100000);
    }

    public StripePayment(String accountEmail, String apiKey, double stripeBalance) {
        this.accountEmail = accountEmail;
        this.apiKey = apiKey;
        this.stripeBalance = stripeBalance;
    }

    @Override
    public boolean validateCredentials() {
        System.out.println("Validating Stripe Account...");
        System.out.println("   Account: " + maskEmail(accountEmail));
        System.out.println("   API Key: " + maskApiKey(apiKey));
        return accountEmail != null && accountEmail.contains("@")
                && apiKey != null && apiKey.startsWith("sk_") && apiKey.length() > 10;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Stripe Payment...");
        System.out.println("   Amount: Rs." + amount);
        System.out.println("   Stripe Balance: Rs." + stripeBalance);

        if (stripeBalance >= amount) {
            stripeBalance -= amount;
            System.out.println("   Payment Successful!");
            System.out.println("   Remaining Balance: Rs." + stripeBalance);
        } else {
            System.out.println("   Insufficient Stripe Balance!");
        }
    }

    @Override
    public void refund(double amount) {
        stripeBalance += amount;
        System.out.println("Stripe Refund Processed: Rs." + amount + " added to Stripe account");
    }

    @Override
    public String getPaymentMethodName() {
        return "Stripe (" + maskEmail(accountEmail) + ")";
    }

    private String maskEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "unknown";
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            return "****";
        }

        int visibleChars = Math.min(2, atIndex);
        return email.substring(0, visibleChars) + "****" + email.substring(atIndex);
    }

    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() <= 8) {
            return "****";
        }

        return apiKey.substring(0, 7) + "****" + apiKey.substring(apiKey.length() - 4);
    }

}
