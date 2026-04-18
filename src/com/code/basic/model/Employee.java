package com.code.basic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {

    private Integer id;
    private String name;
    private String department;
    private Double salary;

    public Employee(Integer id, String name, String department, Double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", department='" + department + "'" +
                ", salary=" + salary +
                '}';
    }

    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John Doe", "IT", 60000.0));
        employees.add(new Employee(2, "Jane Smith", "HR", 76000.0));
        employees.add(new Employee(3, "Peter Jones", "Finance", 80000.0));
        employees.add(new Employee(4, "Mary Williams", "IT", 62000.0));
        employees.add(new Employee(5, "David Brown", "Marketing", 70000.0));
        employees.add(new Employee(6, "Susan Davis", "HR", 76000.0));
        employees.add(new Employee(7, "Robert Miller", "Finance", 85000.0));
        employees.add(new Employee(8, "Linda Wilson", "IT", 65000.0));
        employees.add(new Employee(9, "James Moore", "Marketing", 71000.0));
        employees.add(new Employee(10, "Patricia Taylor", "HR", 73000.0));
        employees.add(new Employee(11, "Michael Anderson", "Finance", 82000.0));
        employees.add(new Employee(12, "Barbara Thomas", "IT", 68000.0));
        employees.add(new Employee(13, "William Jackson", "Marketing", 74000.0));
        employees.add(new Employee(14, "Elizabeth White", "HR", 88000.0));
        employees.add(new Employee(15, "Richard Harris", "Finance", 88000.0));
        return employees;
    }
}