package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
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
        List<Company> expectedResult = asList(new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(expectedResult);
        CompanyService employeeService = new CompanyService(companyRepository);

        // when
        List<Company> actual = employeeService.getAll();
        //then
        assertEquals(2, actual.size());
    }

    @Test
    void should_create_company_when_create_company() {
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

    @Test
    void should_get_company_given_company_id() {
        //given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);

        when(companyRepository.findById(1)).thenReturn(Optional.of(new Company(1, "OOCL", employeeList.size(), employeeList)));

        // when
        Company actualResult = companyService.get(1);

        //then
        Assertions.assertEquals(1, actualResult.getCompanyId());
        Assertions.assertEquals("OOCL", actualResult.getCompanyName());
    }
}