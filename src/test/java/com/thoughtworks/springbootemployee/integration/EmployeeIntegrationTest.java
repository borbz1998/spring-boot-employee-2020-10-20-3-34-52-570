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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
        Employee employee = new Employee(1, "Charlie", 18, 150, "Male", 1);
        employeeRepository.save(employee);

        //when and then
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("S[0].id").isNumber())
                .andExpect(jsonPath("S[0].name").value("Charlie"))
                .andExpect(jsonPath("S[0].age").value(18))
                .andExpect(jsonPath("S[0].gender").value("Male"))
                .andExpect(jsonPath("S[0].salary").value(150));
    }

    @Test
    void should_create_employee_when_get_create_employee() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"id\": 1234,\n" +
                "    \"name\": \"Charlie\",\n" +
                "    \"age\": 18,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 5000\n" +
                "}";
        //when then
        mockMvc.perform(get("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("S[0].id").isNumber())
                .andExpect(jsonPath("S[0].name").value("Charlie"))
                .andExpect(jsonPath("S[0].age").value(18))
                .andExpect(jsonPath("S[0].gender").value("Male"))
                .andExpect(jsonPath("S[0].salary").value(150));

        List<Employee> employeeList = employeeRepository.findAll();
        Assertions.assertEquals(1, employeeList.size());
        Assertions.assertEquals("Charlie", employeeList.get(0).getName());
    }
}
