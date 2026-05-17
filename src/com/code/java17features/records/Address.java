package com.code.java17features.records;

import java.util.List;

public record Address(List<String> lines, String city, String state, String zipCode) {

    public Address {
       // lines = List.copyOf(lines); // Ensure immutability of the list
    }

        
}
