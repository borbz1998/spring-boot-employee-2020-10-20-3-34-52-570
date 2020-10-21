package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Company createCompany(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    public Company getCompany(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public void deleteCompany(Integer companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        company.ifPresent(companyRepository::remove);
    }
}
