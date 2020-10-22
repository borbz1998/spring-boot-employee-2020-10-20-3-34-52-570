package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class EmployeeRepositoryLegacy {
    private List<Employee> employees = new ArrayList<>();

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public void remove(Employee employee) {
        employees.remove(employee);
    }

    public Optional<Employee> findById(Integer employeeId) {
        return employees.stream().filter(employee -> employee.getId().equals(employeeId))
                .findFirst();
    }

    public List<Employee> getByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender()
                .equalsIgnoreCase(gender)).collect(Collectors.toList());
    }

    // TODO: 10/22/2020 Use long for code smell
    public List<Employee> getByPage(Integer page, Integer pageSize) {
        return employees.stream()
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
