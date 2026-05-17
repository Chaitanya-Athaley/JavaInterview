package com.code.java17features.records;

public class Useorder {

    public static void main(String[] args) {
        Order o = new Order.Builder()
        .orderNumber("12345")
        .customerName("Alice").product("Laptop").price(999.99).build();
        System.out.println("Order Number: " + o.getOrderNumber());
        System.out.println("Customer Name: " + o.getCustomerName());
        System.out.println("Product: " + o.getProduct()); 
        
        User u = new User.UserBuilder().name("Bob").age(25).email("t7C6w@example.com").build();
        System.out.println(u);
    }
}
