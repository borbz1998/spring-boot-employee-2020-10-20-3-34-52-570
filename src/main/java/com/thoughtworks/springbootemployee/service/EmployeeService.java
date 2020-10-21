package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee delete(Employee newEmployee) {
        return employeeRepository.remove(newEmployee);
    }

    public Employee get(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public void delete(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        employee.ifPresent(employeeRepository::remove);
    }
}
