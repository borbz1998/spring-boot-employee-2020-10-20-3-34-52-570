package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private List<Company> companyList = new ArrayList<>();

    public List<Company> findAll() {
        return companyList;
    }
}
