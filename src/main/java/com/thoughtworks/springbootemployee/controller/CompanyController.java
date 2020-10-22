package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
