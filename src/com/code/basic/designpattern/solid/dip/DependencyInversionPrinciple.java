package com.code.basic.designpattern.solid.dip;

/**
 * Dependency Inversion Principle (DIP)
 * High-level modules should not depend on low-level modules.
 * Both should depend on abstractions.
 * Depend on interfaces/abstractions, not concrete implementations.
 */

// BAD EXAMPLE - Violates DIP
class MySQLDatabaseBad {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class UserServiceBad {
    private MySQLDatabaseBad database = new MySQLDatabaseBad(); // Depends on concrete class

    public void saveUser(String userName) {
        database.save(userName);
    }
}

// GOOD EXAMPLE - Follows DIP
interface Database {
    void save(String data);
}

class MySQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class MongoDBDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MongoDB: " + data);
    }
}

class PostgreSQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to PostgreSQL: " + data);
    }
}

class UserService {
    private Database database; // Depends on abstraction

    // Constructor injection
    public UserService(Database database) {
        this.database = database;
    }

    public void saveUser(String userName) {
        database.save(userName);
    }
}

// Demo
public class DependencyInversionPrinciple {
    public static void main(String[] args) {
        System.out.println("=== BAD Example (DIP Violation) ===");
        UserServiceBad badService = new UserServiceBad();
        badService.saveUser("Chaitanya");
        System.out.println("Problem: Tightly coupled to MySQLDatabase. Changing database requires code modification.");

        System.out.println("\n=== GOOD Example (DIP Followed) ===");

        // Using MySQL
        UserService userServiceMySQL = new UserService(new MySQLDatabase());
        userServiceMySQL.saveUser("Chaitanya");

        // Using MongoDB - SAME service, different implementation
        UserService userServiceMongoDB = new UserService(new MongoDBDatabase());
        userServiceMongoDB.saveUser("Chaitanya");

        // Using PostgreSQL - SAME service, different implementation
        UserService userServicePostgreSQL = new UserService(new PostgreSQLDatabase());
        userServicePostgreSQL.saveUser("Chaitanya");

        System.out.println("\nBenefit: Loosely coupled code");
        System.out.println("Can switch database implementations without changing UserService");
        System.out.println("Easy to test with mock implementations");
    }
}
