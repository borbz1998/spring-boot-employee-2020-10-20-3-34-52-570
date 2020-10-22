package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepositoryLegacy;
    private EmployeeRepositoryLegacy employeeRepositoryLegacy;

    public CompanyService(CompanyRepository companyRepositoryLegacy, EmployeeRepositoryLegacy employeeRepositoryLegacy) {
        this.companyRepositoryLegacy = companyRepositoryLegacy;
        this.employeeRepositoryLegacy = employeeRepositoryLegacy;
    }

    public List<Company> getAllCompany() {
        return companyRepositoryLegacy.findAll();
    }

    public Company createCompany(Company newCompany) {
        return companyRepositoryLegacy.save(newCompany);
    }

    public Company getCompany(Integer companyId) {
        return companyRepositoryLegacy.findById(companyId).orElse(null);
    }

    public void deleteCompanyEmployee(Integer companyId) {
        companyRepositoryLegacy.findById(companyId)
                .ifPresent(company -> company.getEmployeeList()
                        .forEach(employee -> employeeRepositoryLegacy.remove(employee)));
    }

    public List<Employee> getCompanyEmployee(Integer companyID) {
        return companyRepositoryLegacy.findById(companyID)
                .map(Company::getEmployeeList).orElse(Collections.emptyList());
    }

    public Company updateCompany(Integer companyID, Company updateCompany) {
        companyRepositoryLegacy.findById(companyID).ifPresent(company -> {
            companyRepositoryLegacy.delete(company);
            companyRepositoryLegacy.save(updateCompany);
        });
        return updateCompany;
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return null;
//        return companyRepositoryLegacy.getByPage(page, pageSize);
    }

}
