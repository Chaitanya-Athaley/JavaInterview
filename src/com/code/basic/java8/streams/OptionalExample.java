package com.code.basic.java8.streams;

import java.util.Optional;

public class OptionalExample {

    public static void main(String[] args) {

        // 1. Creating Optional objects
        Optional<String> optionalWithValue = Optional.of("Hello");
        Optional<String> optionalNullable = Optional.ofNullable(null);
        Optional<String> emptyOptional = Optional.empty();

        System.out.println("Optional with value: " + optionalWithValue);
        System.out.println("Optional with null: " + optionalNullable);
        System.out.println("Empty optional: " + emptyOptional);

        // 2. Checking for presence of a value
        System.out.println("\nIs value present? " + optionalWithValue.isPresent());
        System.out.println("Is optional empty? " + emptyOptional.isEmpty()); // Since Java 11

        // 3. Conditional action with ifPresent()
        optionalWithValue.ifPresent(value -> System.out.println("\nValue from ifPresent: " + value));

        // 4. Providing default values with orElse() and orElseGet()
        String value1 = optionalNullable.orElse("Default Value");
        System.out.println("\nValue from orElse: " + value1);

        String value2 = optionalNullable.orElseGet(() -> "Default value from supplier");
        System.out.println("Value from orElseGet: " + value2);

        // 5. Throwing exceptions with orElseThrow()
        try {
            optionalNullable.orElseThrow(() -> new IllegalStateException("Value not present"));
        } catch (IllegalStateException e) {
            System.out.println("\nException from orElseThrow: " + e.getMessage());
        }

        // 6. Transforming values with map() and flatMap()
        String transformedValue = optionalWithValue.map(String::toUpperCase).orElse("Default");
        System.out.println("\nTransformed value with map: " + transformedValue);

        // flatMap is used when the mapping function returns an Optional
        Optional<String> nestedOptional = Optional.of("Nested");
        Optional<String> flatMapped = optionalWithValue.flatMap(s -> nestedOptional);
        System.out.println("flatMapped value: " + flatMapped.orElse("Default"));

        // 7. Filtering values with filter()
        Optional<String> filteredValue = optionalWithValue.filter(s -> s.equals("Hello"));
        System.out.println("\nFiltered value (present): " + filteredValue.orElse("Not present"));

        Optional<String> filteredValueNotPresent = optionalWithValue.filter(s -> s.equals("World"));
        System.out.println("Filtered value (not present): " + filteredValueNotPresent.orElse("Not present"));
    }

    // A common use case: a method that might return a null
    public static Optional<String> findUserById(String id) {
        if (id.equals("123")) {
            return Optional.of("John Doe");
        }
        else {
            return Optional.empty();
        }
    }
}
