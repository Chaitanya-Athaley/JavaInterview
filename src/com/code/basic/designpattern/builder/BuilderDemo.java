package com.code.basic.designpattern.builder;

public class BuilderDemo {

    public static void main(String[] args) {
        User user1 = new User.UserBuilder("John", "Doe")
                .age(30)
                .phone("1234567890")
                .address("123 Main St")
                .build();

        System.out.println(user1);

        User user2 = new User.UserBuilder("Jane", "Doe")
                .age(25)
                // no phone number and address
                .build();

        System.out.println(user2);
    }
}
