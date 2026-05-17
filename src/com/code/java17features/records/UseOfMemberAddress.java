package com.code.java17features.records;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UseOfMemberAddress {

    public static void main(String[] args) {
        Address address = new Address(List.of("123 Main St", "Apt 4B"), "Springfield", "IL", "62704");
        MemberDTO member = new MemberDTO(address, "John Doe");

        System.out.println("Member Name: " + member.name());
        System.out.println("Member Address: " + member.address());
        System.out.println("Address Lines: " + member.address().lines());
        System.out.println("City: " + member.address().city());
        System.out.println("State: " + member.address().state());
        System.out.println("ZIP Code: " + member.address().zipCode());

        // Attempting to modify the address will not work since records are immutable
        // member.address().city("New City"); // This will cause a compilation error
        // member.address().lines().add("New Line"); // This will also cause a compilation error since the list is immutable

        member = new MemberDTO(new Address(Arrays.asList("456 Oak Ave","Apt 7C"), "New City", "NY", "10001"), "John Doe");
        System.out.println("Updated Member Address: " + member.address());


        //---------------------------List.of()--------vs----Arrays.asList()-----------------------------------------//

        // can not modify List.of() since it is immutable
        List<String> lines = List.of("Line 1", "Line 2");
        // lines.add("Line 3"); // This will cause a compilation error since the list is immutable
        List<String> newLines = new ArrayList<>(lines);
        newLines.add("Line 3"); // This will work since we are modifying a new list that we created, not the original immutable list
        System.out.println("New Lines: " + newLines);

        List<String> newLines2 = Arrays.asList("Line A", "Line B");
        //newLines2.add("Line C"); // This will cause a compilation error since the list is immutable

        //---------------------------List.of()--------vs----Arrays.asList()-----------------------------------------//
       
       
       List<String> listlines = new ArrayList<>();
       listlines.add("456 Oak Ave");
       listlines.add("Apt 7C");
       listlines.add("New Line");
         
       Address newAddress = new Address(listlines, "Los Angeles", "CA", "90001");
        newAddress.lines().add("Another Line"); // This will work since we are modifying the list that was passed to the record, not the record itself
        newAddress.lines().add(0, "First Line"); // This will also work since we are modifying the list that was passed to the record, not the record itself
        newAddress.lines().remove("Apt 7C"); // This will also work since we are modifying the list that was passed to the record, not the record itself
        newAddress.lines().remove(0); // This will also work since we are modifying the list that was passed to the record, not the record itself
        member = new MemberDTO(newAddress, member.name());
        System.out.println("Updated Member Address with ArrayList lines: " + member.address());
    }
}
