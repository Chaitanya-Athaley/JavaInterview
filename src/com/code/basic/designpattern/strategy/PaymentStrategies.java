package com.code.basic.designpattern.strategy;

/**
 * STRATEGY PATTERN - Payment Example
 * 
 * Strategy Pattern defines a family of algorithms, encapsulates each one,
 * and makes them interchangeable.
 * 
 * Real-world analogy:
 * When you go to checkout at an online store, you can choose different payment methods:
 * - Credit Card
 * - PayPal
 * - Apple Pay
 * - Google Pay
 * - Cryptocurrency
 * 
 * Each payment method is a different "strategy" to pay.
 * The checkout process remains the same, but the payment method changes.
 */

// ============ STRATEGY INTERFACE ============

/**
 * Strategy Interface: PaymentStrategy
 * All payment methods must implement this interface
 */
interface PaymentStrategy {
    boolean validateCredentials();
    void processPayment(double amount);
    void refund(double amount);
    String getPaymentMethodName();
}

// ============ CONCRETE STRATEGIES ============

/**
 * Strategy 1: Credit Card Payment
 */
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvv;
    private double balance;

    public CreditCardPayment(String cardNumber, String cardHolder, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.balance = 50000; // Available credit
    }

    @Override
    public boolean validateCredentials() {
        System.out.println("💳 Validating Credit Card...");
        System.out.println("   Card Number: " + maskCardNumber(cardNumber));
        System.out.println("   Expiry: " + expiryDate);
        System.out.println("   CVV: Valid");
        return cardNumber.length() == 16 && cvv.length() == 3;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("💳 Processing Credit Card Payment...");
        System.out.println("   Amount: ₹" + amount);
        System.out.println("   Available Credit: ₹" + balance);
        
        if (balance >= amount) {
            balance -= amount;
            System.out.println("   ✅ Payment Successful!");
            System.out.println("   Remaining Credit: ₹" + balance);
        } else {
            System.out.println("   ❌ Insufficient Credit!");
        }
    }

    @Override
    public void refund(double amount) {
        balance += amount;
        System.out.println("💳 Refund Processed: ₹" + amount + " added back to Credit Card");
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card (" + maskCardNumber(cardNumber) + ")";
    }

    private String maskCardNumber(String cardNumber) {
        return "****-****-****-" + cardNumber.substring(12);
    }
}

/**
 * Strategy 2: PayPal Payment
 */
class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;
    private double walletBalance;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
        this.walletBalance = 100000; // Wallet balance
    }

    @Override
    public boolean validateCredentials() {
        System.out.println("🅿️ Validating PayPal Account...");
        System.out.println("   Email: " + maskEmail(email));
        System.out.println("   Status: Verified");
        return email.contains("@") && password.length() >= 6;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("🅿️ Processing PayPal Payment...");
        System.out.println("   Amount: ₹" + amount);
        System.out.println("   Wallet Balance: ₹" + walletBalance);
        
        if (walletBalance >= amount) {
            walletBalance -= amount;
            System.out.println("   ✅ Payment Successful!");
            System.out.println("   Remaining Balance: ₹" + walletBalance);
        } else {
            System.out.println("   ❌ Insufficient Wallet Balance!");
        }
    }

    @Override
    public void refund(double amount) {
        walletBalance += amount;
        System.out.println("🅿️ Refund Processed: ₹" + amount + " added to PayPal Wallet");
    }

    @Override
    public String getPaymentMethodName() {
        return "PayPal (" + maskEmail(email) + ")";
    }

    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        return email.substring(0, 2) + "****" + email.substring(atIndex);
    }
}

/**
 * Strategy 3: Google Pay
 */
class GooglePayPayment implements PaymentStrategy {
    private String phoneNumber;
    private String upiId;
    private boolean biometricEnabled;
    private double upiBalance;

    public GooglePayPayment(String phoneNumber, String upiId) {
        this.phoneNumber = phoneNumber;
        this.upiId = upiId;
        this.biometricEnabled = true;
        this.upiBalance = 75000;
    }

    @Override
    public boolean validateCredentials() {
        System.out.println("🤖 Validating Google Pay...");
        System.out.println("   Phone: " + maskPhoneNumber(phoneNumber));
        System.out.println("   UPI ID: " + upiId);
        System.out.println("   Biometric: " + (biometricEnabled ? "Enabled ✓" : "Disabled"));
        return upiId.contains("@") && phoneNumber.length() == 10;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("🤖 Processing Google Pay Payment...");
        System.out.println("   Amount: ₹" + amount);
        System.out.println("   UPI Balance: ₹" + upiBalance);
        
        if (upiBalance >= amount) {
            upiBalance -= amount;
            System.out.println("   ✅ Payment Successful!");
            System.out.println("   Remaining Balance: ₹" + upiBalance);
        } else {
            System.out.println("   ❌ Insufficient UPI Balance!");
        }
    }

    @Override
    public void refund(double amount) {
        upiBalance += amount;
        System.out.println("🤖 Refund Processed: ₹" + amount + " added to Google Pay");
    }

    @Override
    public String getPaymentMethodName() {
        return "Google Pay (" + upiId + ")";
    }

    private String maskPhoneNumber(String phone) {
        return "****" + phone.substring(6);
    }
}

/**
 * Strategy 4: Apple Pay
 */
class ApplePayPayment implements PaymentStrategy {
    private String deviceId;
    private String tokenizedData;
    private double appleCashBalance;

    public ApplePayPayment(String deviceId, String tokenizedData) {
        this.deviceId = deviceId;
        this.tokenizedData = tokenizedData;
        this.appleCashBalance = 60000;
    }

    @Override
    public boolean validateCredentials() {
        System.out.println("🍎 Validating Apple Pay...");
        System.out.println("   Device ID: " + maskDeviceId(deviceId));
        System.out.println("   Token: Secured ✓");
        return tokenizedData.length() > 0 && deviceId.length() > 0;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("🍎 Processing Apple Pay Payment...");
        System.out.println("   Amount: ₹" + amount);
        System.out.println("   Apple Cash Balance: ₹" + appleCashBalance);
        
        if (appleCashBalance >= amount) {
            appleCashBalance -= amount;
            System.out.println("   ✅ Payment Successful!");
            System.out.println("   Remaining Balance: ₹" + appleCashBalance);
        } else {
            System.out.println("   ❌ Insufficient Apple Cash!");
        }
    }

    @Override
    public void refund(double amount) {
        appleCashBalance += amount;
        System.out.println("🍎 Refund Processed: ₹" + amount + " added to Apple Cash");
    }

    @Override
    public String getPaymentMethodName() {
        return "Apple Pay (" + maskDeviceId(deviceId) + ")";
    }

    private String maskDeviceId(String deviceId) {
        return deviceId.substring(0, 4) + "****" + deviceId.substring(8);
    }
}

/**
 * Strategy 5: Cryptocurrency (Bitcoin)
 */
class CryptoPayment implements PaymentStrategy {
    private String walletAddress;
    private double bitcoinBalance;
    private double btcToRupeesRate;

    public CryptoPayment(String walletAddress, double bitcoinBalance) {
        this.walletAddress = walletAddress;
        this.bitcoinBalance = bitcoinBalance;
        this.btcToRupeesRate = 2500000; // 1 BTC = 25 Lakhs INR (approximate)
    }

    @Override
    public boolean validateCredentials() {
        System.out.println("₿ Validating Bitcoin Wallet...");
        System.out.println("   Wallet: " + maskWallet(walletAddress));
        System.out.println("   BTC Balance: " + bitcoinBalance);
        return walletAddress.length() >= 26 && bitcoinBalance > 0;
    }

    @Override
    public void processPayment(double amountInRupees) {
        double amountInBtc = amountInRupees / btcToRupeesRate;
        System.out.println("₿ Processing Bitcoin Payment...");
        System.out.println("   Amount (INR): ₹" + amountInRupees);
        System.out.println("   Amount (BTC): " + String.format("%.8f", amountInBtc));
        System.out.println("   Wallet Balance: " + bitcoinBalance + " BTC");
        
        if (bitcoinBalance >= amountInBtc) {
            bitcoinBalance -= amountInBtc;
            System.out.println("   ✅ Payment Successful!");
            System.out.println("   Remaining Balance: " + String.format("%.8f", bitcoinBalance) + " BTC");
        } else {
            System.out.println("   ❌ Insufficient Bitcoin!");
        }
    }

    @Override
    public void refund(double amountInRupees) {
        double amountInBtc = amountInRupees / btcToRupeesRate;
        bitcoinBalance += amountInBtc;
        System.out.println("₿ Refund Processed: ₹" + amountInRupees + 
                         " (" + String.format("%.8f", amountInBtc) + " BTC) added to wallet");
    }

    @Override
    public String getPaymentMethodName() {
        return "Bitcoin (" + maskWallet(walletAddress) + ")";
    }

    private String maskWallet(String wallet) {
        return wallet.substring(0, 6) + "****" + wallet.substring(wallet.length() - 6);
    }
}
