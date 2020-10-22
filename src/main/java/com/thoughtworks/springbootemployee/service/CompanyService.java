package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private EmployeeRepositoryLegacy employeeRepositoryLegacy;
    private EmployeeRepository employeeRepository;

//    public CompanyService(CompanyRepository companyRepositoryLegacy, EmployeeRepositoryLegacy employeeRepositoryLegacy) {
//        this.companyRepositoryLegacy = companyRepositoryLegacy;
//        this.employeeRepositoryLegacy = employeeRepositoryLegacy;
//    }

    public CompanyService(CompanyRepository companyRepositoryLegacy) {
        this.companyRepository = companyRepositoryLegacy;
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

//    public void deleteCompanyEmployee(Integer companyId) {
//        companyRepository.findById(companyId)
//                .ifPresent(company -> company.getEmployeeList()
//                        .forEach(employee -> employeeRepository.delete(employee)));
//    }

    public void deleteCompanyEmployee(Integer companyId) {
        companyRepository.deleteById(companyId);

    }

    public List<Employee> getCompanyEmployee(Integer companyID) {
        return companyRepository.findById(companyID)
                .map(Company::getEmployeeList).orElse(Collections.emptyList());
    }

    public Company updateCompany(Integer companyID, Company updateCompany) {
        companyRepository.findById(companyID).ifPresent(company -> {
            companyRepository.delete(company);
            companyRepository.save(updateCompany);
        });
        return updateCompany;
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        PageRequest pageable = PageRequest.of(page, pageSize);
        return companyRepository.findAll(pageable).toList();
//        return null;
//        return companyRepositoryLegacy.getByPage(page, pageSize);
    }

}
