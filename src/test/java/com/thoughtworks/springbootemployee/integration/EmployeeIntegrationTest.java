package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void should_get_all_employee_when_get_all() throws Exception {
        //given
        Employee employee = new Employee(1, "Charlie", 18, 150, "Male");
        employeeRepository.save(employee);

        //when and then
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Charlie"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(150));
    }

    @Test
    void should_create_employee_when_get_create_employee() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Charlie\",\n" +
                "    \"age\": 18,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 5000\n" +
                "}";
        //when then
        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(5000));

        List<Employee> employeeList = employeeRepository.findAll();
        Assertions.assertEquals(1, employeeList.size());
        Assertions.assertEquals("Charlie", employeeList.get(0).getName());
    }

    @Test
    void should_find_employee_when_given_employee_id() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Charlie\",\n" +
                "    \"age\": 18,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 5000\n" +
                "}";
        //when then
        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(5000));

        List<Employee> employeeList = employeeRepository.findAll();

        mockMvc.perform(get("/employee/"+employeeList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(5000));

        Optional<Employee> employee = employeeRepository.findById(1);
        Assertions.assertEquals(1, employee.get().getId());
        Assertions.assertEquals("Charlie", employee.get().getName());
        Assertions.assertEquals(18, employee.get().getAge());
        Assertions.assertEquals("Male", employee.get().getGender());
        Assertions.assertEquals(5000, employee.get().getSalary());
    }

    @Test
    void should_update_employee_when_given_employee_id() throws Exception {
        //given
        Employee employee = new Employee(1, "Tom", 18,  5000,"Male");
        employeeRepository.save(employee);

//        String employeeAsJson = "{\n" +
//                "    \"id\": 1,\n" +
//                "    \"name\": \"Charlie\",\n" +
//                "    \"age\": 18,\n" +
//                "    \"gender\": \"Male\",\n" +
//                "    \"salary\": 5000\n" +
//                "}";
//        //when then
//        mockMvc.perform(post("/employee")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(employeeAsJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.name").value("Charlie"))
//                .andExpect(jsonPath("$.age").value(18))
//                .andExpect(jsonPath("$.gender").value("Male"))
//                .andExpect(jsonPath("$.salary").value(5000));

//        List<Employee> employeeList = employeeRepository.findAll();

        String updateEmployeeAsJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Leo\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 2500\n" +
                "}";
        //when then
        mockMvc.perform(put("/employee/"+1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Leo"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(2500));

        mockMvc.perform(get("/employee/"+1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Leo"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(2500));


        Optional<Employee> updateEmployee = employeeRepository.findById(1);
        Assertions.assertEquals(1, updateEmployee.get().getId());
        Assertions.assertEquals("Leo", updateEmployee.get().getName());
        Assertions.assertEquals(20, updateEmployee.get().getAge());
        Assertions.assertEquals("Male", updateEmployee.get().getGender());
        Assertions.assertEquals(2500, updateEmployee.get().getSalary());
    }
}
