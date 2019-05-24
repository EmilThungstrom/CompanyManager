package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.forms.EmployeeForm;
import se.lexicon.emil.CompanyManager.service.EmployeeService;
import se.lexicon.emil.CompanyManager.testing.EntityGeneration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeRestController.class)
@AutoConfigureMockMvc
class EmployeeRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EmployeeService employeeService;

    static EntityGeneration entityGeneration;

    static {
        try {
            entityGeneration = new EntityGeneration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Employee employee1;
    static Employee employee2;
    static Employee employee3;
    static Employee employee4;
    static Employee employee5;

    @BeforeAll
    public static void init(){
        employee1 = entityGeneration.createEmployee(null, null);
        employee2 = entityGeneration.createEmployee(null, null);
        employee3 = entityGeneration.createEmployee(null, null);
        employee4 = entityGeneration.createEmployee(null, null);
        employee5 = entityGeneration.createEmployee(null, null);

        employee3.setFirstName(employee4.getFirstName());
        employee5.setAddress(employee4.getAddress());
        employee5.setLastName(employee4.getLastName());
    }

    @Test
    void findall() throws Exception {
        List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4, employee5);
        when(employeeService.findAll()).thenReturn(employees);
        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));
    }

    @Test
    void findById() throws Exception {
        when(employeeService.findById(4)).thenReturn(employee4);
        mockMvc.perform(get("/api/employee")
                .param("id", "4"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employee4)));
    }

    @Test
    void findByForm() throws Exception {
        List<Employee> employees = Arrays.asList(employee4, employee5);
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setFirstName(employee4.getFirstName());
        employeeForm.setLastName(employee4.getLastName());
        employeeForm.setAddress(employee4.getAddress());
        employeeForm.setEmail(employee4.getEmail());

        when(employeeService.findByForm(employeeForm)).thenReturn(employees);

        mockMvc.perform(post("/api/employee")
                .content(objectMapper.writeValueAsString(employeeForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));
    }

    @Test
    void createEmployee() throws Exception {
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setFirstName(employee1.getFirstName());
        employeeForm.setLastName(employee1.getLastName());
        employeeForm.setAddress(employee1.getAddress());
        employeeForm.setEmail(employee1.getEmail());

        when(employeeService.createEmployee(employeeForm)).thenReturn(employee1);
        mockMvc.perform(post("/api/employee/create")
                .content(objectMapper.writeValueAsString(employeeForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(employeeService).createEmployee(employeeForm);
    }

    @Test
    void updateEmployee() throws Exception {

        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setFirstName(employee1.getFirstName());
        employeeForm.setLastName(employee1.getLastName());
        employeeForm.setAddress(employee1.getAddress());
        employeeForm.setEmail(employee1.getEmail());

        when(employeeService.updateEmployee(employeeForm)).thenReturn(employee1);
        mockMvc.perform(post("/api/employee/update")
                .content(objectMapper.writeValueAsString(employeeForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(employeeService).updateEmployee(employeeForm);
    }

    @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(post("/api/employee/delete")
                .content("4")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(employeeService).deleteEmployee(4);
    }
}