package com.code.basic.designpattern.singleton;

public class EagerInitializedSingleton {

    private static final EagerInitializedSingleton instance = new EagerInitializedSingleton();

    private EagerInitializedSingleton() {
        // private constructor to prevent instantiation
    }

    public static EagerInitializedSingleton getInstance() {
        return instance;
    }

    public void showMessage(){
        System.out.println("Hello from Eager Initialized Singleton!");
    }
}
