package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    @Test
    void should_return_company_list_when_get_all_given_companies() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        List<Company> expectedResult = asList(new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(expectedResult);
        CompanyService employeeService = new CompanyService(companyRepository,employeeRepository);

        // when
        List<Company> actual = employeeService.getAllCompany();
        //then
        assertEquals(2, actual.size());
    }

    @Test
    void should_create_company_when_create_company() {
        //given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        Company companyRequest = new Company(1, "OOCL", employeeList.size(), employeeList);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        when(companyRepository.save(companyRequest)).thenReturn(companyRequest);
        CompanyService companyService = new CompanyService(companyRepository,employeeRepository);

        // when
        Company actualResult = companyService.createCompany(companyRequest);

        //then
        Assertions.assertEquals(1, actualResult.getCompanyId());
    }

    @Test
    void should_get_company_given_company_id() {
        //given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository,employeeRepository);

        when(companyRepository.findById(1)).thenReturn(Optional
                .of(new Company(1, "OOCL", employeeList.size(), employeeList)));

        // when
        Company actualResult = companyService.getCompany(1);

        //then
        Assertions.assertEquals(1, actualResult.getCompanyId());
        Assertions.assertEquals("OOCL", actualResult.getCompanyName());
    }

    @Test
    void should_delete_company_given_company_id() {
        // given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository,employeeRepository);

        when(companyRepository.findById(1)).thenReturn(Optional
                .of(new Company(1, "OOCL", employeeList.size(), employeeList)));

        // when
        companyService.deleteCompany(1);

        //then
        Mockito.verify(companyRepository, Mockito.times(1)).remove(Mockito.any(Company.class));
    }
}