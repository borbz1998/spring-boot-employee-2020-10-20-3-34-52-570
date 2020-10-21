package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        List<Employee> expectedResult = asList(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(expectedResult);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        List<Employee> actual = employeeService.getAllEmployees();
        //then
        assertEquals(2, actual.size());
    }

    @Test
    void should_create_employee_when_create_employee() {
        //given
        Employee employeeRequest = new Employee(1, "Charlie", 22, 150, "Male");
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        when(employeeRepository.save(employeeRequest)).thenReturn(employeeRequest);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        Employee actualResult = employeeService.createEmployee(employeeRequest);

        //then
        Assertions.assertEquals(1, actualResult.getId());
    }


    @Test
    void should_get_employee_given_employee_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        when(employeeRepository.findById(1)).thenReturn(Optional
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
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        when(employeeRepository.findById(1)).thenReturn(Optional
                .of(new Employee(1, "Charlie", 18, 150, "Male")));

        // when
        employeeService.deleteEmployee(1);

        //then
        Mockito.verify(employeeRepository, Mockito.times(1))
                .remove(Mockito.any(Employee.class));
    }

    @Test
    void should_return_updated_employee_given_employee_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        when(employeeRepository.findById(1)).thenReturn(Optional
                .of(new Employee(1, "Charlie", 18, 150, "Male")));

        // when
        employeeService.updateEmployee(1,
                (new Employee(2, "Janelle", 18, 150, "female")));

        //then
        when(employeeRepository.findById(2)).thenReturn(Optional
                .of(new Employee(2, "Charlie", 18, 150, "female")));
    }

    @Test
    void should_return_gender_when_search_given_employee_request() {
        // GIVEN
        Employee employeeRequest = new Employee(1, "junjun", 10, 150, "male");
        Employee employeeRequest2 = new Employee(2, "Charlie", 10, 150, "male");
        Employee employeeRequest3 = new Employee(2, "Charlie", 10, 150, "female");

        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.createEmployee(employeeRequest);
        employeeService.createEmployee(employeeRequest2);
        employeeService.createEmployee(employeeRequest3);
        //when
        List<Employee> employeeList = employeeService.getByGender("Male");
        // then
        Assertions.assertEquals(2, employeeList.size());
    }

    @Test
    void should_return_gender_when_search_given_3_employee_request() {
        //GIVEN
        Employee employeeRequest = new Employee(1, "junjun", 10, 150, "male");
        Employee employeeRequest2 = new Employee(2, "Charlie", 10, 150, "male");
        Employee employeeRequest3 = new Employee(2, "Charlie", 10, 150, "female");

        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        when(repository.findAll()).thenReturn(asList(employeeRequest, employeeRequest2, employeeRequest3));
        EmployeeService employeeService = new EmployeeService(repository);

        List<Employee> employeeList = asList(employeeRequest, employeeRequest2);
        when(repository.getByGender("male")).thenReturn(employeeList);
        List<Employee> actual = employeeService.getByGender("male");
        Assertions.assertEquals(2, actual.size());
    }

}
