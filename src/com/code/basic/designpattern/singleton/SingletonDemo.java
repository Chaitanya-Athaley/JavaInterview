package com.code.basic.designpattern.singleton;

public class SingletonDemo {

    public static void main(String[] args) {
        // Get the only object available
        Singleton object = Singleton.getInstance();

        // Show the message
        object.showMessage();
    }
}
