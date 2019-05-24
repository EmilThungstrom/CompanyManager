package se.lexicon.emil.CompanyManager.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.exceptions.EntityNotFoundException;
import se.lexicon.emil.CompanyManager.forms.EmployeeForm;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;
import se.lexicon.emil.CompanyManager.testing.EntityGeneration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeServiceImplTest {

    @MockBean
    DepartmentRepository departmentRepository;
    @MockBean
    EmployeeRepository employeeRepository;
    @MockBean
    TeamRepository teamRepository;

    @TestConfiguration
    static class DepartmentServiceImplTestContextConfiguration {

        @Bean
        @Autowired
        public EmployeeService employeeService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, TeamRepository teamRepository) {
            return new EmployeeServiceImpl(departmentRepository, employeeRepository, teamRepository);
        }
    }

    @Autowired
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
    void findAll() {
        List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4, employee5);
        when(employeeRepository.findAll()).thenReturn(employees);
        assertEquals(employees, employeeService.findAll());
    }

    @Test
    void findById() {
        when(employeeRepository.findById(3)).thenReturn(Optional.of(employee3));
        assertEquals(employee3, employeeService.findById(3));

        when(employeeRepository.findById(7)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(7));
    }

    @Test
    void findByForm() {
        List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4, employee5);
        when(employeeRepository.findAll()).thenReturn(employees);

        EmployeeForm employeeForm1 = new EmployeeForm();

        employeeForm1.setFirstName(employee4.getFirstName());
        employeeForm1.setLastName(employee4.getLastName());
        employeeForm1.setAddress(employee4.getAddress());
        employeeForm1.setEmail(employee4.getEmail());

        assertEquals(Arrays.asList(employee4), employeeService.findByForm(employeeForm1));

        EmployeeForm employeeForm2 = new EmployeeForm();
        employeeForm2.setFirstName(employee4.getFirstName());

        assertEquals(Arrays.asList(employee3, employee4), employeeService.findByForm(employeeForm2));

        EmployeeForm employeeForm3 = new EmployeeForm();
        employeeForm3.setLastName(employee4.getLastName());
        employeeForm3.setAddress(employee4.getAddress());

        assertEquals(Arrays.asList(employee4, employee5), employeeService.findByForm(employeeForm3));
    }
}