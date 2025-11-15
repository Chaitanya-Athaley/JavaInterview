package com.code.basic.java8.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.code.basic.model.Employee;

public class SecondHighestPaidEmployeeInEachDepartment {

	public static void main(String[] args) {
		List<Employee> employees = Employee.getEmployees();
		Map<String, Employee> collect = employees.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment,
						Collectors.collectingAndThen(Collectors.toList(),
								list->list.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(1).findFirst().get())));
		collect.entrySet().stream().forEach(el-> System.out.println(el.getKey()+"__________"+el.getValue()));
	}

}
