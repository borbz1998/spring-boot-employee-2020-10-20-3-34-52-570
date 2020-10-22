package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {
    @Test
    void should_return_list_employee_when_get_all_given_employees() {
        //given
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        List<Employee> expectedResult = asList(new Employee(), new Employee());
        when(employeeRepositoryLegacy.findAll()).thenReturn(expectedResult);
        EmployeeService employeeService = new EmployeeService(employeeRepositoryLegacy);

        // when
        List<Employee> actual = employeeService.getAllEmployees();

        //then
        assertEquals(2, actual.size());
    }

    @Test
    void should_create_employee_when_create_employee() {
        //given
        Employee employeeRequest = new Employee(1, "Charlie", 22, 150, "Male");
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        when(employeeRepositoryLegacy.save(employeeRequest)).thenReturn(employeeRequest);
        EmployeeService employeeService = new EmployeeService(employeeRepositoryLegacy);

        // when
        Employee actualResult = employeeService.createEmployee(employeeRequest);

        //then
        Assertions.assertEquals(1, actualResult.getId());
    }


    @Test
    void should_get_employee_given_employee_id() {
        //given
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        EmployeeService employeeService = new EmployeeService(employeeRepositoryLegacy);
        when(employeeRepositoryLegacy.findById(1)).thenReturn(Optional
                .of(new Employee(1, "Charlie", 18, 150, "Male")));

        // when
        Employee actualResult = employeeService.getEmployee(1);

        //then
        Assertions.assertEquals(1, actualResult.getId());
        Assertions.assertEquals("Charlie", actualResult.getName());
    }


    @Test
    void should_delete_employee_given_employee_id() {
        //given
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        EmployeeService employeeService = new EmployeeService(employeeRepositoryLegacy);

        when(employeeRepositoryLegacy.findById(1)).thenReturn(Optional
                .of(new Employee(1, "Charlie", 18, 150, "Male")));

        // when
        employeeService.deleteEmployee(1);

        //then
        Mockito.verify(employeeRepositoryLegacy, Mockito.times(1))
                .remove(Mockito.any(Employee.class));
    }

    @Test
    void should_return_updated_employee_given_employee_id() {
        // given
        Employee employeeRequest = new Employee(1, "Charlie", 22, 150, "Male");
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);
        when(employeeRepositoryLegacy.save(employeeRequest)).thenReturn(employeeRequest);
        EmployeeService employeeService = new EmployeeService(employeeRepositoryLegacy);

        // when
        Employee actualResult = employeeService.updateEmployee(1,
                (new Employee(1, "Janelle", 18, 150, "female")));

        // then
        Assertions.assertEquals("Janelle", actualResult.getName());
        // AssertSame
    }

    @Test
    void should_return_employee_list_given_employee_gender_is_male() {
        // GIVEN
        Employee employeeRequest = new Employee(1, "junjun", 10, 150, "male");
        Employee employeeRequest2 = new Employee(2, "Charlie", 10, 150, "male");
        Employee employeeRequest3 = new Employee(2, "Charlie", 10, 150, "female");

        EmployeeRepositoryLegacy employeeRepositoryLegacy = new EmployeeRepositoryLegacy();
        EmployeeService employeeService = new EmployeeService(employeeRepositoryLegacy);
        employeeService.createEmployee(employeeRequest);
        employeeService.createEmployee(employeeRequest2);
        employeeService.createEmployee(employeeRequest3);

        //when
        List<Employee> employeeList = employeeService.getByGender("Male");

        // then
        Assertions.assertEquals(2, employeeList.size());
    }

    @Test
    void should_return_2_employee_when_getByPage_given_employee_request() {
        // GIVEN
        List<Employee> employeeList = asList(new Employee(), new Employee());
        EmployeeRepositoryLegacy employeeRepositoryLegacy = Mockito.mock(EmployeeRepositoryLegacy.class);

        // WHEN
        when(employeeRepositoryLegacy.getByPage(1, 2)).thenReturn(employeeList);
        EmployeeService employeeService = new EmployeeService(employeeRepositoryLegacy);
        List<Employee> employeeActual = employeeService.getByPage(1, 2);

        // THEN
        Assertions.assertEquals(2, employeeActual.size());
    }
}
