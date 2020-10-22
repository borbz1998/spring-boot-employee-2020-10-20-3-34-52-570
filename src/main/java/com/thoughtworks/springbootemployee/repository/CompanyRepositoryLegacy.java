package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompanyRepositoryLegacy {
    private List<Company> companyList = new ArrayList<>();

    public List<Company> findAll() {
        return companyList;
    }

    public Company save(Company newCompany) {
        companyList.add(newCompany);
        return newCompany;
    }

    public Optional<Company> findById(Integer companyId) {
        return companyList.stream()
                .filter(company -> company
                        .getCompany_Id().equals(companyId))
                .findFirst();
    }

    public void remove(Company company) {
        companyList.remove(company);
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return companyList.stream()
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .collect(Collectors.toList());

    }
}
