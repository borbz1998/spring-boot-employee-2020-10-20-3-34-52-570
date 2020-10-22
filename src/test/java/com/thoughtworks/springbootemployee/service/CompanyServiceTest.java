package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepositoryLegacy;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
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
        CompanyRepositoryLegacy companyRepositoryLegacy = Mockito.mock(CompanyRepositoryLegacy.class);
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        List<Company> expectedResult = asList(new Company(), new Company());
        when(companyRepositoryLegacy.findAll()).thenReturn(expectedResult);
        CompanyService companyService = new CompanyService(companyRepositoryLegacy, employeeRepositoryLegacy);

        // when
        List<Company> actual = companyService.getAllCompany();
        //then
        assertEquals(2, actual.size());
    }

    @Test
    void should_return_created_company_when_create_company() {
        //given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        Company companyRequest = new Company(1, "OOCL", employeeList.size(), employeeList);
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        CompanyRepositoryLegacy companyRepositoryLegacy = Mockito.mock(CompanyRepositoryLegacy.class);
        when(companyRepositoryLegacy.save(companyRequest)).thenReturn(companyRequest);
        CompanyService companyService = new CompanyService(companyRepositoryLegacy, employeeRepositoryLegacy);

        // when
        Company actualResult = companyService.createCompany(companyRequest);

        //then
        Assertions.assertEquals(1, actualResult.getCompanyId());
    }

    @Test
    void should_get_specific_company_given_company_id() {
        //given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        CompanyRepositoryLegacy companyRepositoryLegacy = Mockito.mock(CompanyRepositoryLegacy.class);
        CompanyService companyService = new CompanyService(companyRepositoryLegacy, employeeRepositoryLegacy);

        when(companyRepositoryLegacy.findById(1)).thenReturn(Optional
                .of(new Company(1, "OOCL", employeeList.size(), employeeList)));

        // when
        Company actualResult = companyService.getCompany(1);

        //then
        Assertions.assertEquals(1, actualResult.getCompanyId());
        Assertions.assertEquals("OOCL", actualResult.getCompanyName());
        // Assert Same actual
    }

    @Test
    void should_delete_employee_on_list_given_company_id() {
        // given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        CompanyRepositoryLegacy companyRepositoryLegacy = Mockito.mock(CompanyRepositoryLegacy.class);
        CompanyService companyService = new CompanyService(companyRepositoryLegacy, employeeRepositoryLegacy);

        when(companyRepositoryLegacy.findById(1)).thenReturn(Optional
                .of(new Company(1, "OOCL", employeeList.size(), employeeList)));

        // when
        companyService.deleteCompanyEmployee(1);

        //then
        Mockito.verify(employeeRepositoryLegacy, Mockito.times(2)).remove(Mockito.any(Employee.class));
    }

    @Test
    void should_return_2_company_when_getByPage_given_company_request() {
        //GIVEN
        List<Employee> employeeList = asList(new Employee(), new Employee());
        List<Employee> employeeList2 = asList(new Employee(), new Employee());
        List<Company> companies =
                asList(new Company(1, "OOCL", employeeList.size(), employeeList),
                        new Company(1, "OOL", employeeList2.size(), employeeList2));

        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        CompanyRepositoryLegacy companyRepositoryLegacy = Mockito.mock(CompanyRepositoryLegacy.class);
        when(companyRepositoryLegacy.getByPage(1, 2)).thenReturn(companies);
        CompanyService companyService = new CompanyService(companyRepositoryLegacy, employeeRepositoryLegacy);
        //WHEN
        List<Company> companyActual = companyService.getByPage(1, 2);
        //THEN
        Assertions.assertEquals(2, companyActual.size());
    }

    @Test
    void should_return_company_employees_on_list_given_company_id() {
        // given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        CompanyRepositoryLegacy companyRepositoryLegacy = Mockito.mock(CompanyRepositoryLegacy.class);
        CompanyService companyService = new CompanyService(companyRepositoryLegacy, employeeRepositoryLegacy);

        when(companyRepositoryLegacy.findById(1)).thenReturn(Optional
                .of(new Company(1, "OOCL", employeeList.size(), employeeList)));

        // when
        List<Employee> finalEmployee = companyService.getCompanyEmployee(1);

        //then
        Assertions.assertEquals(2, finalEmployee.size());
    }

    // TODO: 10/22/2020 Should create test that will check if the List of employee is empty 

    @Test
    void should_return_updated_company_given_company_id() {
        // given
        List<Employee> employeeList = asList(new Employee(), new Employee());
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        CompanyRepositoryLegacy companyRepositoryLegacy = Mockito.mock(CompanyRepositoryLegacy.class);

        Company company = new Company(123, "OOCL", employeeList.size(), employeeList);

        when(companyRepositoryLegacy.save(company)).thenReturn(company);
        CompanyService companyService = new CompanyService(companyRepositoryLegacy, employeeRepositoryLegacy);
        // when
        companyService.createCompany(company);
        List<Employee> newEmployeeList = asList(new Employee(), new Employee(), new Employee());

        // when
        Company actualResult = companyService.updateCompany(123,
                (new Company(123, "JANELLE INC.", newEmployeeList.size(), newEmployeeList)));

        // then
        Assertions.assertEquals("JANELLE INC.", actualResult.getCompanyName());
        Assertions.assertEquals(3, actualResult.getEmployeeList().size());
    }
}