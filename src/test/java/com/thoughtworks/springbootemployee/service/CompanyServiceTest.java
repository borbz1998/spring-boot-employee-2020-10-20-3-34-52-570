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

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
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

//    @Test
//    void should_return_2_company_when_getByPage_given_company_request() {
//        //GIVEN
//        List<Employee> employeeList = asList(new Employee(), new Employee());
//        List<Employee> employeeList2 = asList(new Employee(), new Employee());
//        List<Company> companies =
//                asList(new Company(1, "OOCL", employeeList.size(), employeeList),
//                        new Company(1, "OOL", employeeList2.size(), employeeList2));
//
//        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
//
//        when(companyRepositoryLegacy.getByPage(1, 2)).thenReturn(companies);
//        CompanyService companyService = new CompanyService(companyRepositoryLegacy);
//        //WHEN
//        List<Company> companyActual = companyService.getByPage(1, 2);
//        //THEN
//        Assertions.assertEquals(2, companyActual.size());
//    }

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