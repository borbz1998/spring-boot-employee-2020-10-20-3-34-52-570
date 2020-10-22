package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyServiceTest {
    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;
    private List<Employee> finalEmployeeList;

    @BeforeEach
    void setup(){
        companyRepository = Mockito.mock(CompanyRepository.class);
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        finalEmployeeList = asList(new Employee(), new Employee());
    }


    @Test
    void should_return_company_list_when_get_all_given_companies() {
        //given

        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        List<Company> expectedResult = asList(new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(expectedResult);
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        List<Company> actual = companyService.getAllCompany();
        //then
        assertEquals(2, actual.size());
    }

    @Test
    void should_return_created_company_when_create_company() {
        //given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        Company companyRequest = new Company(1, "OOCL",  employeeList);
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);

        when(companyRepository.save(companyRequest)).thenReturn(companyRequest);
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        Company actualResult = companyService.createCompany(companyRequest);

        //then
        Assertions.assertEquals(1, actualResult.getCompany_Id());
    }

    @Test
    void should_get_specific_company_given_company_id() {
        //given
        List<Employee> employeeList = asList(new Employee(), new Employee());

        CompanyService companyService = new CompanyService(companyRepository);

        when(companyRepository.findById(1)).thenReturn(Optional
                .of(new Company(1, "OOCL",  employeeList)));

        // when
        Company actualResult = companyService.getCompany(1);

        //then
        Assertions.assertEquals(1, actualResult.getCompany_Id());
        Assertions.assertEquals("OOCL", actualResult.getCompanyName());
        // Assert Same actual
    }

    @Test
    void should_delete_employee_on_list_given_company_id() {
        // given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        EmployeeRepository employeeRepositoryLegacy = Mockito.mock(EmployeeRepository.class);

        CompanyService companyService = new CompanyService(companyRepository);

        // when
        companyService.deleteCompanyEmployee(1);

        //then
        Mockito.verify(companyRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));
    }

    @Test
    void should_return_2_company_when_getByPage_given_company_request() {

        Company companyRequest = new Company(1,"OOCL",new ArrayList<>());
        Company companyRequest2 = new Company(2,"COSCO",new ArrayList<>());
        Company companyRequest3 = new Company(3,"OOCL",new ArrayList<>());
        Company companyRequest4 = new Company(4,"OOCL",new ArrayList<>());
        //given
        Page<Company> mockPage = mock(Page.class);
        when(companyRepository.save(companyRequest)).thenReturn(companyRequest);
        when(companyRepository.save(companyRequest2)).thenReturn(companyRequest2);
        when(companyRepository.save(companyRequest3)).thenReturn(companyRequest3);
        when(companyRepository.save(companyRequest4)).thenReturn(companyRequest4);
        when(companyRepository.findAll(PageRequest.of(0, 2))).thenReturn(mockPage);
        when(mockPage.toList()).thenReturn(asList(companyRequest, companyRequest2));
        CompanyService companyService = new CompanyService(companyRepository);
        //when
        List<Company> fetchedCompany = companyService.getByPage(0,2);
        assertEquals(2, fetchedCompany.size());
    }

    @Test
    void should_return_company_employees_on_list_given_company_id() {
        // given
        List<Employee> employeeList = asList(new Employee(), new Employee());

        CompanyService companyService = new CompanyService(companyRepository);

        when(companyRepository.findById(1)).thenReturn(Optional
                .of(new Company(1, "OOCL",  employeeList)));

        // when
        List<Employee> finalEmployee = companyService.getCompanyEmployee(1);

        //then
        Assertions.assertEquals(2, finalEmployee.size());
    }

    // TODO: 10/22/2020 Should create test that will check if the List of employee is empty

    @Test
    void should_return_updated_company_given_company_id() {
        // given
        List<Employee> employeeList = asList(new Employee(1, "Charlie", 18, 150, "Male"),
                new Employee(2, "Leo", 18, 150, "Male"));

        Company company = new Company(1, "OOCL",  employeeList);

        when(companyRepository.save(company)).thenReturn(company);
        CompanyService companyService = new CompanyService(companyRepository);
        // when
        companyService.createCompany(company);
        List<Employee> newEmployeeList = asList(new Employee(), new Employee(), new Employee());

        // when
        Company actualResult = companyService.updateCompany(1,
                (new Company(1, "JANELLE INC.", newEmployeeList)));

        // then
        Assertions.assertEquals("JANELLE INC.", actualResult.getCompanyName());
        Assertions.assertEquals(3, actualResult.getEmployeeList().size());
    }
}