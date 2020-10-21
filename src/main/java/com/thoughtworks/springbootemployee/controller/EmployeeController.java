package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
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
    public Employee getByID(@PathVariable Integer employeeID) {
        return employees.stream().filter(employee -> employee.getId()
                .equals(employeeID)).findFirst().orElse(null);
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam("gender") String gender) {
        return employees.stream().filter(employee -> employee.getGender()
                .equalsIgnoreCase(gender)).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getByPage(
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        return employees.stream()
                .skip(pageSize * page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee newEmployee) {
        employees.add(newEmployee);
        return newEmployee;
    }

    @DeleteMapping({"/{employeeID}"})
    public void delete(@PathVariable Integer employeeID) {
        employees.stream().filter(employee -> employee.getId()
                .equals(employeeID)).findFirst().ifPresent(employees::remove);
    }

    @PutMapping(path = "/{employeeID}")
    public Employee updateEmployee(@PathVariable Integer employeeID, @RequestBody Employee updateEmployee) {
        employees.stream()
                .filter(employee -> employeeID.equals(employee.getId()))
                .findFirst()
                .ifPresent(employee -> {
                    employees.remove(employee);
                    employees.add(updateEmployee);
                });
        return updateEmployee;
    }
}
