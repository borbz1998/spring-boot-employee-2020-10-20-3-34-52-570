package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {
    @Test
    void should_return_list_employee_when_get_all_given_employees() {
        //given
        EmployeeService employeeService = new EmployeeService();
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        List<Employee> expectedResult = asList(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(expectedResult);

        // when
        List<Employee> actual = employeeService.getAll();
        //then
        assertEquals(2, actual.size());
    }

}