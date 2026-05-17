package com.code.java17features.records;

public class Order {

    private final String orderNumber;
    private final String customerName;
    private final String product;
    private final double price;

    private Order(Builder builder) {
        this.orderNumber = builder.orderNumber;
        this.customerName = builder.customerName;
        this.product = builder.product;
        this.price = builder.price;

    }

    public String getOrderNumber() {
        return orderNumber;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getProduct() {
        return product;
    }    
    public double getPrice() {
        return price;
    }   

    public static class Builder{
        private String orderNumber;
        private String customerName;
        private String product;
        private double price;

        public Builder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder product(String product) {
            this.product = product;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
