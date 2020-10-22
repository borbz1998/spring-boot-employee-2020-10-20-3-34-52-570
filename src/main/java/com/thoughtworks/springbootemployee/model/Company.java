package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    private Integer companyId;
    private String companyName;
    private Integer employeesNumber;
    @OneToMany(
            fetch = FetchType.LAZY,
            orphanRemoval = false
    )
    @JoinColumn(name = "company_id")
    private List<Employee> employeeList;

    public Company() {
    }

    public Company(Integer companyId, String companyName, Integer employeesNumber, List<Employee> employeeList) {
        this.companyId = companyId;
        this.companyName = companyName;
        // TODO: 10/22/2020 remove employees number 
        this.employeesNumber = employeesNumber;
        this.employeeList = employeeList;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // TODO: 10/22/2020 Return employee size 
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
