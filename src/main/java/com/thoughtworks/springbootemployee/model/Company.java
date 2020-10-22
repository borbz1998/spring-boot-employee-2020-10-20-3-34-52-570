package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    private Integer company_Id;
    private String companyName;
    private Integer employeesNumber;
    @OneToMany(
            fetch = FetchType.LAZY,
            orphanRemoval = false
    )
    @JoinColumn(name = "company_Id")
    private List<Employee> employeeList;

    public Company() {
    }

    public Company(Integer company_Id, String companyName, List<Employee> employeeList) {
        this.company_Id = company_Id;
        this.companyName = companyName;
        // TODO: 10/22/2020 remove employees number 
        this.employeesNumber = employeeList.size();
        this.employeeList = employeeList;
    }

    public Integer getCompany_Id() {
        return company_Id;
    }

    public void setCompany_Id(Integer company_Id) {
        this.company_Id = company_Id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
