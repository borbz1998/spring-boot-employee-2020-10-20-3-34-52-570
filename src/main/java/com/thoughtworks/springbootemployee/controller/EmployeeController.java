package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService =
            new EmployeeService(employeeRepository);

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping({"/{employeeID}"})
    public Employee getByID(@PathVariable Integer employeeID) {
        return employeeService.getAllEmployees().stream().filter(employee -> employee.getId()
                .equals(employeeID)).findFirst().orElse(null);
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam("gender") String gender) {
        return employeeService.getByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getByPage(
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        return employeeService.getByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee newEmployee) {
        return employeeService.createEmployee(newEmployee);
    }

    @DeleteMapping({"/{employeeID}"})
    public void deleteEmployee(@PathVariable Integer employeeID) {
        employeeService.getAllEmployees().stream().filter(employee -> employee.getId()
                .equals(employeeID)).findFirst().ifPresent(employeeService.getAllEmployees()::remove);
    }

    @PutMapping(path = "/{employeeID}")
    public Employee updateEmployee(@PathVariable Integer employeeID, @RequestBody Employee newEmployee) {
        return employeeService.updateEmployee(employeeID, newEmployee);
    }
}
