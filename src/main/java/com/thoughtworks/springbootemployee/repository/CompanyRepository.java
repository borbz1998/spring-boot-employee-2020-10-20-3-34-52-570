package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository {
    private List<Company> companyList = new ArrayList<>();

    public List<Company> findAll() {
        return companyList;
    }

    public Company save(Company newCompany) {
        companyList.add(newCompany);
        return newCompany;
    }

    public Optional<Company> findById(Integer companyId) {
        return companyList.stream().filter(company -> company.getCompanyId().equals(companyId))
                .findFirst();
    }

    public Company remove(Company company) {
        companyList.remove(company);
        return company;
    }
}
