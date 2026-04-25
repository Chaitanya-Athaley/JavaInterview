package com.code.basic.java8.streams;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.code.basic.model.Employee;

public class EmployeeBonus {

	public static void main(String[] args) {
		// get employees eligible for bonus (salary > 5000), group them by department,
		// and within each department sort employees by salary in descending order using
		// Java streams
		List<Employee> employees = Employee.getEmployees();
		Map<String, List<Employee>> collect3 = employees.stream()
				.filter(emp -> emp.getSalary()>60000)
				.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(Collectors.toList(), list -> list.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(Collectors.toList()))));
		collect3.entrySet().stream().forEach(n-> System.out.println(n.getKey()+"___"+n.getValue()));

		Map<String, Double> collect = employees.stream()
		.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)))
		.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldv,newv) -> oldv, LinkedHashMap::new));
		
		//.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		System.out.println(collect);
	}

}
