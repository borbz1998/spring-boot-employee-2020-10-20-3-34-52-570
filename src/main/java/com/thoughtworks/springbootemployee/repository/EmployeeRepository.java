package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee save(Employee newEmployee) {
        employees.add(newEmployee);
        return newEmployee;
    }

    public Employee remove(Employee removeEmployee) {
        employees.remove(removeEmployee);
        return removeEmployee;
    }

    public Optional<Employee> findById(Integer employeeId) {
        return employees.stream().filter(employee -> employee.getId().equals(employeeId))
                .findFirst();
    }
}
