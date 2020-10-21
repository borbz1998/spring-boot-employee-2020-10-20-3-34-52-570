package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import jdk.jfr.internal.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService =
            new EmployeeService(new EmployeeRepository());

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping({"/{employeeID}"})
    public Employee getByID(@PathVariable Integer employeeID) {
        return employeeService.getAll().stream().filter(employee -> employee.getId()
                .equals(employeeID)).findFirst().orElse(null);
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam("gender") String gender) {
        return employeeService.getAll().stream().filter(employee -> employee.getGender()
                .equalsIgnoreCase(gender)).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getByPage(
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        return employeeService.getAll().stream()
                .skip(pageSize * page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee newEmployee) {
        employeeService.getAll().add(newEmployee);
        return newEmployee;
    }

    @DeleteMapping({"/{employeeID}"})
    public void delete(@PathVariable Integer employeeID) {
        employeeService.getAll().stream().filter(employee -> employee.getId()
                .equals(employeeID)).findFirst().ifPresent(employeeService.getAll()::remove);
    }

    @PutMapping(path = "/{employeeID}")
    public Employee updateEmployee(@PathVariable Integer employeeID, @RequestBody Employee updateEmployee) {
        employeeService.getAll().stream()
                .filter(employee -> employeeID.equals(employee.getId()))
                .findFirst()
                .ifPresent(employee -> {
                    employeeService.getAll().remove(employee);
                    employeeService.getAll().add(updateEmployee);
                });
        return updateEmployee;
    }
}
