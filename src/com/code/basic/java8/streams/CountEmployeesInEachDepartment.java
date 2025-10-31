package com.code.basic.java8.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.code.basic.model.Employee;

public class CountEmployeesInEachDepartment {

	public static void main(String[] args) {
		List<Employee> employees = Employee.getEmployees(); 
		Map<String, Long> collect = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
		System.out.println(collect);
	}

}
