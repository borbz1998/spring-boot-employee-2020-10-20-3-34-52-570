package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAll() {
        return employees;
    }

    @GetMapping({"/{employeeID}"})
    public Employee get(@PathVariable Integer employeeID) {
        return employees.stream().filter(employee -> employee.getId()
                .equals(employeeID)).findFirst().orElse(null);
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam("gender") String gender) {
        return employees.stream().filter(employee -> employee.getGender()
                .equalsIgnoreCase(gender)).collect(Collectors.toList());
    }
}
