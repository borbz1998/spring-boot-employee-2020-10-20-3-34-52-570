package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private List<Employee> employees =  new ArrayList<>();

    //    @GetMapping(path = "/{userName}")
    @GetMapping
    public List<Employee> getAll() {
        return employees;
    }
}
