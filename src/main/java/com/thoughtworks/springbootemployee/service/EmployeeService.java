package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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
        employeeRepository.deleteById(employeeId);
    }

    public Employee updateEmployee(Integer employeeId, Employee newEmployee) {
        newEmployee.setId(employeeId);
        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        PageRequest pageable = PageRequest.of(page, pageSize);
        return employeeRepository.findAll(pageable).toList();
//        return employeeRepository.getByPage(page, pageSize);
    }
}
