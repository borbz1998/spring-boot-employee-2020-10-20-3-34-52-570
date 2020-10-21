package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
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

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        companies.add(company);
        return company;
    }


    @DeleteMapping({"/{companyID}"})
    public void delete(@PathVariable Integer companyID) {
        companies.stream().filter(company -> company.getCompanyId()
                .equals(companyID)).findFirst().ifPresent(companies::remove);
    }

    @PutMapping({"/{companyID}"})
    public Company updateCompany(@PathVariable Integer companyID, @RequestBody Company updateCompany) {
        companies.stream()
                .filter(company -> companyID.equals(company.getCompanyId()))
                .findFirst()
                .ifPresent(employee -> {
                    companies.remove(employee);
                    companies.add(updateCompany);
                });
        return updateCompany;
    }

}
