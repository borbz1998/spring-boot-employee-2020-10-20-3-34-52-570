package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getAllCompanies() {
        return companies;
    }

    @GetMapping({"/{companyID}"})
    public Company getByID(@PathVariable Integer companyID) {
        return companies.stream().filter(company -> company.getCompanyId()
                .equals(companyID)).findFirst().orElse(null);
    }

}
