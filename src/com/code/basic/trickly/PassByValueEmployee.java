package com.code.basic.trickly;

import com.code.basic.model.Employee;

public class PassByValueEmployee {

	public static void main(String[] args) {
		Employee e1 = new Employee(99, "Jack", "Sparrow", 50030);
		System.out.println("Before changeReference(): " + e1);
		changeReference(e1);
		System.out.println("After changeReference(): " + e1);
	}

	static void changeReference(Employee employee) {
		employee = new Employee(101, "Tatya", "bicchu", 2020);// This ONLY affects local copy
	}

	static void modifyObject(Employee employee) {
		employee.setName("Modified"); // This affects the original object
		employee.setSalary(25);
	}

}
