package com.code.basic.java8.streams;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.code.basic.model.Employee;

public class CountEmployeesInEachDepartment {

	public static void main(String[] args) {
		List<Employee> employees = Employee.getEmployees(); 
		Map<String, Long> insertionOrder = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,LinkedHashMap::new, Collectors.counting()));
		System.out.println(insertionOrder);
		Map<String, Long> normalSortOrder = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,TreeMap::new, Collectors.counting()));
		System.out.println(normalSortOrder);

	}

}
