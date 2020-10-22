package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        employeeRepository = mock(EmployeeRepository.class);
    }

    @Test
    void should_return_list_employee_when_get_all_given_employees() {
        //given
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
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        when(employeeRepository.findById(1)).thenReturn(Optional
                .of(new Employee(1, "Charlie", 18, 150, "Male")));

        // when
        employeeService.deleteEmployee(1);

        //then
        Mockito.verify(employeeRepository, Mockito.times(1))
                .deleteById(Mockito.any(Integer.class));
    }

    @Test
    void should_return_updated_employee_given_employee_id() {
        // given
        Employee employeeRequest = new Employee(1, "Charlie", 22, 150, "Male");
        when(employeeRepository.save(employeeRequest)).thenReturn(employeeRequest);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        employeeService.createEmployee(employeeRequest);
        Employee updateResult = employeeService.updateEmployee(1,
                (new Employee(1, "Leo", 18, 600, "Female")));

        // then
        Assertions.assertEquals("Leo", updateResult.getName());
        Assertions.assertEquals(18, updateResult.getAge());
        Assertions.assertEquals(600, updateResult.getSalary());
        Assertions.assertEquals("Female", updateResult.getGender());
    }

    @Test
    void should_return_employee_list_given_employee_gender_is_male() {
        // GIVEN
        Employee employeeRequest = new Employee(1, "junjun", 10, 150, "Male");
        Employee employeeRequest2 = new Employee(2, "Charlie", 10, 150, "Male");
        when(employeeRepository.findByGender("Male")).thenReturn(asList(employeeRequest, employeeRequest2));
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when
        List<Employee> employeeList = employeeService.getByGender("Male");

        // then
        assertEquals(2, employeeList.size());
    }

    @Test
    void should_return_2_employee_when_getByPage_given_employee_request() {
        Employee employeeRequest = new Employee(1, "junjun", 10, 150, "Male");
        Employee employeeRequest2 = new Employee(2, "Charlie", 18, 69, "Male");
        Employee employeeRequest3 = new Employee(3, "Leo", 35, 55, "Male");
        Employee employeeRequest4 = new Employee(4, "Leo", 35, 55, "Male");

        //given
        Page<Employee> mockPage = mock(Page.class);
        when(employeeRepository.save(employeeRequest)).thenReturn(employeeRequest);
        when(employeeRepository.save(employeeRequest2)).thenReturn(employeeRequest2);
        when(employeeRepository.save(employeeRequest3)).thenReturn(employeeRequest3);
        when(employeeRepository.save(employeeRequest4)).thenReturn(employeeRequest4);
        when(employeeRepository.findAll(PageRequest.of(0, 2))).thenReturn(mockPage);
        when(mockPage.toList()).thenReturn(asList(employeeRequest, employeeRequest2));
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        //when
        List<Employee> fetchedEmployees = employeeService.getByPage(0,2);

        //then
        assertEquals(2, fetchedEmployees.size());
    }
}
