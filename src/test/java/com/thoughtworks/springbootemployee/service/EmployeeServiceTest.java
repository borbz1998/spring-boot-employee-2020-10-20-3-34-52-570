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
        List<Employee> actual = employeeService.getAll();
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
        Employee actualResult = employeeService.create(employeeRequest);

        //then
        Assertions.assertEquals(1, actualResult.getId());
    }

    @Test
    void should_return_employee_when_employee_is_deleted() {
        //given
        Employee employeeRequest = new Employee(1, "Charlie", 22, 150, "Male");
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        when(employeeRepository.save(employeeRequest)).thenReturn(employeeRequest);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        Employee actualResult = employeeService.create(employeeRequest);
//        when(employeeRepository.remove(employeeRequest)).thenReturn(employeeRequest);
//        Employee actualResult = employeeService.delete(employeeRequest);
        int actualSize = employeeRepository.getEmployees().size();

        //then

        Assertions.assertEquals(1, actualSize);
    }

    @Test
    void should_get_employee_given_employee_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee(1, "Charlie", 18, 150, "Male")));

        // when
        Employee actualResult = employeeService.get(1);

        //then
        Assertions.assertEquals(1, actualResult.getId());
        Assertions.assertEquals("Charlie", actualResult.getName());
    }
}
