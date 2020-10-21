package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> findAll() {
        return employees;

    }


    public Employee save(Employee newEmployee) {
        employees.add(newEmployee);
        return newEmployee;
    }

}
