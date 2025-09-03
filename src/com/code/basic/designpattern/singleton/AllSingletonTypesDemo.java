package com.code.basic.designpattern.singleton;

public class AllSingletonTypesDemo {

    public static void main(String[] args) {

        // Eager Initialized Singleton
        EagerInitializedSingleton eagerInstance = EagerInitializedSingleton.getInstance();
        eagerInstance.showMessage();

        // Static Block Singleton
        StaticBlockSingleton staticBlockInstance = StaticBlockSingleton.getInstance();
        staticBlockInstance.showMessage();

        // Thread Safe Singleton
        ThreadSafeSingleton threadSafeInstance = ThreadSafeSingleton.getInstance();
        threadSafeInstance.showMessage();

        // Bill Pugh Singleton
        BillPughSingleton billPughInstance = BillPughSingleton.getInstance();
        billPughInstance.showMessage();

        // Enum Singleton
        EnumSingleton enumInstance = EnumSingleton.INSTANCE;
        enumInstance.showMessage();

        // Double Checked Locking Singleton
        DoubleCheckedLockingSingleton doubleCheckedInstance = DoubleCheckedLockingSingleton.getInstance();
        doubleCheckedInstance.showMessage();
    }
}