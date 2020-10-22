package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

        mockMvc.perform(get("/employee/{employeeId}", 1))
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
        Employee employee = new Employee(1, "Tom", 18, 5000, "Male");
        employeeRepository.save(employee);
        String updateEmployeeAsJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Leo\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 2500\n" +
                "}";
        //when then
        mockMvc.perform(put("/employee/{employeeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Leo"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(2500));

        mockMvc.perform(get("/employee/{employeeId}", 1))
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

    @Test
    void should_delete_employee_when_delete_given_company_id() throws Exception {
        //given
        Employee employee = new Employee(1, "Leo", 16, 5000, "Male");
        employeeRepository.save(employee);

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/{employeeId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void should_find_all_employee_when_filter_given_gender_is_Male() throws Exception {
        //given
        Employee firstEmployee = new Employee(1, "Leo", 16, 5000, "Male");
        Employee secondEmployee = new Employee(2, "Charlie", 16, 5000, "Male");
        Employee thirdEmployee = new Employee(3, "Olive", 16, 5000, "Female");
        employeeRepository.save(firstEmployee);
        employeeRepository.save(secondEmployee);
        employeeRepository.save(thirdEmployee);
        //when then
        mockMvc.perform(get("/employee?gender=Male")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Leo"))
                .andExpect(jsonPath("$[0].age").value(16))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(5000))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("Charlie"))
                .andExpect(jsonPath("$[1].age").value(16))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(5000));
        List<Employee> employees = employeeRepository.findByGender("Male");
        Assertions.assertEquals(2, employees.size());
    }

    @Test
    void should_return_page_1_and_2_for_employee_when_pagination_given_page_1_page_size_2() throws Exception {
        //given
        Employee firstEmployee = new Employee(1, "Leo", 16, 5000, "Male");
        Employee secondEmployee = new Employee(2, "Charlie", 16, 5000, "Male");
        Employee thirdEmployee = new Employee(3, "Olive", 16, 5000, "Female");
        employeeRepository.save(firstEmployee);
        employeeRepository.save(secondEmployee);
        employeeRepository.save(thirdEmployee);
        //when
        mockMvc.perform(get("/employee?page=0&pageSize=2")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Leo"))
                .andExpect(jsonPath("$[0].age").value(16))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(5000))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("Charlie"))
                .andExpect(jsonPath("$[1].age").value(16))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(5000));
        //then
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> employees = employeeService.getByPage(0, 2);
        Assertions.assertEquals(2, employees.size());
        Assertions.assertEquals(1, employees.get(0).getId());
        Assertions.assertEquals("Leo", employees.get(0).getName());
        Assertions.assertEquals(16, employees.get(0).getAge());
        Assertions.assertEquals("Male", employees.get(0).getGender());
        Assertions.assertEquals(5000, employees.get(0).getSalary());
    }
}
