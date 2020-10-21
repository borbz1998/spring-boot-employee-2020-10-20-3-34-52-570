package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteCompanyEmployee(Integer companyId) {
        companyRepository.findById(companyId)
                .ifPresent(company -> company.getEmployeeList()
                        .forEach(employee -> employeeRepository.remove(employee)));
    }

    public List<Employee> getCompanyEmployee(Integer companyID) {
        return companyRepository.findById(companyID)
                .map(Company::getEmployeeList).orElse(null);
    }

    public Company updateCompany(Integer companyID, Company updateCompany) {
        companyRepository.findById(companyID).ifPresent(company -> {
            companyRepository.remove(company);
            companyRepository.save(updateCompany);
        });
        return updateCompany;
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return companyRepository.getByPage(page, pageSize);
    }

}
