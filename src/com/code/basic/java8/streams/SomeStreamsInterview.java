package com.code.basic.java8.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.code.basic.model.Employee;

public class SomeStreamsInterview {

    public static void main(String[] args) {
        List<Employee> employees = Employee.getEmployees();

        System.out.println("Count of employees in each department:");
        countEmployeesInEachDepartment(employees);

       // Map<String,Long> d = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println("\nAverage salary of each department:");
        averageSalaryOfEachDepartment(employees);

        System.out.println("\nHighest paid employee in each department:");
        highestPaidEmployeeInEachDepartment(employees);

        System.out.println("\nSecond highest paid employee:");
        secondHighestPaidEmployee(employees);
        
        System.out.println("\nHighest paid employee:");
        employeeWithHighestSalary(employees);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 2, 4, 6);
        System.out.println("\nSum of even numbers: " + sumOfEvenNumbers(numbers));

        String str = "ilovejavaprogramming";
        System.out.println("\nCharacter count: ");
        characterCount(str);

        List<String> strings = Arrays.asList("java", "programming", "is", "fun");
        System.out.println("\nStrings sorted by length: " + sortStringsByLength(strings));
        
        System.out.println("\nDuplicate numbers: " + findDuplicateNumbers(numbers));

    }

    public static void countEmployeesInEachDepartment(List<Employee> employees) {
        Map<String, Long> departmentCount = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        departmentCount.forEach((department, count) -> System.out.println(department + ": " + count));
    }

    public static void averageSalaryOfEachDepartment(List<Employee> employees) {
        Map<String, Double> avgSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        avgSalary.forEach((department, avg) -> System.out.println(department + ": " + avg));
    }

    public static void highestPaidEmployeeInEachDepartment(List<Employee> employees) {
        Map<String, Optional<Employee>> highestPaid = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));
        highestPaid.forEach((department, employee) -> System.out.println(department + ": " + (employee.isPresent() ? employee.get().getName() : "N/A")));
    }

    public static void secondHighestPaidEmployee(List<Employee> employees) {
        Optional<Employee> secondHighest = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(1)
                .findFirst();
        secondHighest.ifPresent(employee -> System.out.println("Second highest paid employee: " + employee));
    }
    
    public static void employeeWithHighestSalary(List<Employee> employees) {
        Optional<Employee> highestPaid = employees.stream()
                .collect(Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)));
        highestPaid.ifPresent(employee -> System.out.println("Highest paid employee: " + employee));
    }

    public static int sumOfEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static void characterCount(String str) {
        Map<Character, Long> charCount = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        charCount.forEach((character, count) -> System.out.println(character + ": " + count));
    }

    public static List<String> sortStringsByLength(List<String> strings) {
        return strings.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
    }
    
    public static List<Integer> findDuplicateNumbers(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}