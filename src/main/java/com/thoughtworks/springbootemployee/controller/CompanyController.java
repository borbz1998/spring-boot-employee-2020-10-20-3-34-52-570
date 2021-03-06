package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;
    private EmployeeService employeeService;

    public CompanyController(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompany();
    }

    @GetMapping({"/{companyID}"})
    public Company getByID(@PathVariable Integer companyID) {
        return companyService.getCompany(companyID);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return company;
    }


    @DeleteMapping({"/{companyID}"})
    public void delete(@PathVariable Integer companyID) {
        companyService.deleteCompanyEmployee(companyID);
    }

    @PutMapping({"/{companyID}"})
    public Company updateCompany(@PathVariable Integer companyID, @RequestBody Company updateCompany) {
        companyService.updateCompany(companyID, updateCompany);
        return updateCompany;
    }


    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getByPage(
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        return companyService.getByPage(page, pageSize);
    }

    @GetMapping({"/{companyID}/employee"})
    public List<Employee> getEmployees(@PathVariable Integer companyID) {
        return companyService.getCompanyEmployee(companyID);
    }
}
