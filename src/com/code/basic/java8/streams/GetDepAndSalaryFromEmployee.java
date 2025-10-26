package com.code.basic.java8.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.code.basic.model.Employee;

public class GetDepAndSalaryFromEmployee {

	public static void main(String[] args) {
		List<Employee> emplist = Employee.getEmployees();
		 Map<String, List<Double>> collect = emplist.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getSalary, Collectors.toList() )));
		 collect.entrySet().forEach(n->{
			 System.out.println(n.getKey()+" "+n.getValue()); 
		 });
	}

}
