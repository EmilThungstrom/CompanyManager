package se.lexicon.emil.CompanyManager.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.exceptions.EntityNotFoundException;
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
public class DepartmentServiceImplTest {

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
        public DepartmentService departmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, TeamRepository teamRepository) {
            return new DepartmentServiceImpl(departmentRepository, employeeRepository, teamRepository);
        }
    }

    @Autowired
    DepartmentService departmentService;

    static EntityGeneration entityGeneration;

    static {
        try {
            entityGeneration = new EntityGeneration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Department department1;
    static Department department2;
    static Department department3;
    static Department department4;
    static Department department5;

    @BeforeAll
    public static void init(){
        department1 = entityGeneration.createDepartment("department1");
        department2 = entityGeneration.createDepartment("department2");
        department3 = entityGeneration.createDepartment("department3");
        department4 = entityGeneration.createDepartment("department4");
        department5 = entityGeneration.createDepartment("department5");

        department3.setHead(department5.getHead());
    }

    @Test
    public void findAll() {
        List<Department> departments = Arrays.asList(department1, department2, department3, department4, department5);
        when(departmentRepository.findAll()).thenReturn(departments);
        assertEquals(departments, departmentService.findAll());
    }

    @Test
    public void findByNameContaining() {
        List<Department> departments = Arrays.asList(department1, department2, department3, department4, department5);
        when(departmentRepository.findByNameContaining("department")).thenReturn(departments);
        assertEquals(departments, departmentService.findByNameContaining("department"));

        List<Department> onlyDepartment3 = Arrays.asList(department3);
        when(departmentRepository.findByNameContaining("department3")).thenReturn(onlyDepartment3);
        assertEquals(onlyDepartment3, departmentService.findByNameContaining("department3"));

        List<Department> empty = Arrays.asList();
        when(departmentRepository.findByNameContaining("departments")).thenReturn(empty);
        assertEquals(empty, departmentService.findByNameContaining("departments"));
    }

    @Test
    public void findByHeadId() {
        Employee headDepartment2 = department2.getHead();
        List<Department> onlyDepartment2 = Arrays.asList(department2);
        when(employeeRepository.findById(0)).thenReturn(Optional.of(headDepartment2));
        when(departmentRepository.findByHead(headDepartment2)).thenReturn(onlyDepartment2);
        assertEquals(onlyDepartment2, departmentService.findByHeadId(0));

        Employee headDepartment3A5= department3.getHead();
        List<Department> departments3A5 = Arrays.asList(department3, department5);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(headDepartment3A5));
        when(departmentRepository.findByHead(headDepartment3A5)).thenReturn(departments3A5);
        assertEquals(departments3A5, departmentService.findByHeadId(1));

        when(employeeRepository.findById(11)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.findByHeadId(11));
    }

    @Test
    public void findById() {
        when(departmentRepository.findById(3)).thenReturn(Optional.of(department3));
        assertEquals(department3, departmentService.findById(3));

        when(departmentRepository.findById(7)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.findById(11));
    }

    @Test
    public void createDepartment() {
        Department department6 = new Department("department6", null);
        when(departmentRepository.save(department6)).thenReturn(department6);
        assertEquals(department6, departmentService.createDepartment("department6"));
    }

    @Test
    public void deleteDepartment_Department_EntityNotFoundException() {
        when(departmentRepository.findById(7)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.deleteDepartment(7));
    }

    @Test
    public void assignEmployees_Employee_EntityNotFoundException() {
        Department department = entityGeneration.createDepartment("department");
        Employee employee1 = entityGeneration.createEmployee(department3, null);
        int[] employeeIds = {11, 55, 62};

        when(departmentRepository.findById(6)).thenReturn(Optional.of(department));
        when(employeeRepository.findById(11)).thenReturn(Optional.of(employee1));
        when(employeeRepository.findById(55)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.assignEmployees(6, employeeIds));
    }

    @Test
    public void assignEmployees_Department_EntityNotFoundException() {
        when(departmentRepository.findById(7)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.assignEmployees(7, new int[]{11}));
    }

    @Test
    public void deleteEmployees() {
        Employee employee = department2.getEmployees().get(0);

        when(departmentRepository.findById(1)).thenReturn(Optional.of(department1));
        when(employeeRepository.findById(32)).thenReturn(Optional.of(employee));
        assertThrows(IllegalAccessException.class, () -> departmentService.deleteEmployees(1, new int[] {32}));
    }

    @Test
    public void addTeam() {
        when(departmentRepository.findById(7)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.addTeam(7));
    }

    @Test
    public void deleteTeam_EntityNotFoundException() {
        when(departmentRepository.findById(7)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.deleteTeam(7, 7));
    }

    @Test
    public void deleteTeam_IllegalAccessException() {
        Team team = department5.getTeams().get(0);
        when(departmentRepository.findById(3)).thenReturn(Optional.of(department3));
        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        assertThrows(IllegalAccessException.class, () -> departmentService.deleteTeam(3, 7));
    }
}