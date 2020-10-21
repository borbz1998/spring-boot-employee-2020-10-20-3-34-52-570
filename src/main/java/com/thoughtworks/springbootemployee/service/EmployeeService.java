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

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee getEmployee(Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .orElse(null);
    }

    public void deleteEmployee(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        employee.ifPresent(employeeRepository::remove);
    }

    public Employee updateEmployee(Integer employeeId, Employee newEmployee) {
        employeeRepository.findById(employeeId)
                .ifPresent(employee -> {
                    employeeRepository.remove(employee);
                    employeeRepository.save(newEmployee);
                });
        return newEmployee;
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepository.getByGender(gender);
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        return employeeRepository.getByPage(page, pageSize);
    }
}
