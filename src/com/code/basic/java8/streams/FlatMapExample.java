package com.code.basic.java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Customer {
    private String name;
    private List<String> phoneNumbers;

    public Customer(String name, List<String> phoneNumbers) {
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}

public class FlatMapExample {

    public static void main(String[] args) {
        // Simple example with a list of lists
        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        List<Integer> flattenedList = listOfLists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        System.out.println("Flattened list: " + flattenedList);

        // Example with a list of custom objects
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John", Arrays.asList("123-456-7890", "098-765-4321")));
        customers.add(new Customer("Jane", Arrays.asList("111-222-3333", "444-555-6666")));

        List<String> allPhoneNumbers = customers.stream()
                .flatMap(customer -> customer.getPhoneNumbers().stream())
                .collect(Collectors.toList());

        System.out.println("All phone numbers: " + allPhoneNumbers);
    }
}
