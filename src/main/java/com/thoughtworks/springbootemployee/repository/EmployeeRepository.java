package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> findAll() {
        return employees;

    }
}
