package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    @Test
    void should_return_list_employee_when_get_all_given_employees() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        List<Company> expectedResult = asList(new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(expectedResult);
        CompanyService employeeService = new CompanyService(companyRepository);

        // when
        List<Company> actual = employeeService.getAll();
        //then
        assertEquals(2, actual.size());
    }

    @Test
    void should_create_employee_when_create_employee() {
        //given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        Company companyRequest = new Company(1, "OOCL", employeeList.size(), employeeList);
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        when(companyRepository.save(companyRequest)).thenReturn(companyRequest);
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        Company actualResult = companyService.create(companyRequest);

        //then
        Assertions.assertEquals(1, actualResult.getCompanyId());
    }

}