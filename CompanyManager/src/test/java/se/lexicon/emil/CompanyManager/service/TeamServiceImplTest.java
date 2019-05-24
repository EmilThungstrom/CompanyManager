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
class TeamServiceImplTest {

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
        public TeamService teamService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, TeamRepository teamRepository) {
            return new TeamServiceImpl(departmentRepository, employeeRepository, teamRepository);
        }
    }

    @Autowired
    TeamService teamService;

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

    static Team team1;
    static Team team2;
    static Team team3;
    static Team team4;
    static Team team5;

    static Employee leader;

    @BeforeAll
    public static void init(){
        department1 = new Department("test01", null);
        department2 = new Department("test02", null);

        team1 = entityGeneration.createTeam(department1, 5);
        team2 = entityGeneration.createTeam(department2, 6);
        team3 = entityGeneration.createTeam(department1, 7);
        team4 = entityGeneration.createTeam(department2, 8);
        team5 = entityGeneration.createTeam(department2, 9);

        leader = entityGeneration.createEmployee(department2, team4);
    }

    @Test
    void findAll() {
        List<Team> teams = Arrays.asList(team1, team2, team3, team4, team5);
        when(teamRepository.findAll()).thenReturn(teams);
        assertEquals(teams, teamService.findAll());
    }

    @Test
    void findByDepartment() {
        List<Team> teams = Arrays.asList(team2, team4, team5);

        when(departmentRepository.findById(2)).thenReturn(Optional.of(department2));
        when(teamRepository.findByDepartment(department2)).thenReturn(teams);

        assertEquals(teams, teamService.findByDepartment(2));
    }

    @Test
    void findByLeader() {
        List<Team> teams = Arrays.asList(team4);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(leader));
        when(teamRepository.findByLeader(leader)).thenReturn(teams);

        assertEquals(teams, teamService.findByLeader(1));
    }

    @Test
    void findById() {
        when(teamRepository.findById(1)).thenReturn(Optional.of(team1));
        assertEquals(team1, teamService.findById(1));
    }

    @Test
    void assignEmployees_IllegalAccessException() {
        Employee employee1 = entityGeneration.createEmployee(department1, null);
        Employee employee2 = entityGeneration.createEmployee(department2, null);
        Employee employee3 = entityGeneration.createEmployee(department1, null);
        int[] employees = { 1, 2, 3 };

        when(teamRepository.findById(1)).thenReturn(Optional.of(team1));
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee1));
        when(employeeRepository.findById(2)).thenReturn(Optional.of(employee2));
        when(employeeRepository.findById(3)).thenReturn(Optional.of(employee3));

        assertThrows(IllegalAccessException.class, () -> teamService.assignEmployees(1, employees));
    }

    @Test
    void unassignEmployees_IllegalAccessException() {
        Employee employee1 = entityGeneration.createEmployee(department1, team1);
        Employee employee2 = entityGeneration.createEmployee(department2, team2);
        Employee employee3 = entityGeneration.createEmployee(department1, team1);
        int[] employees = { 1, 2, 3 };

        when(teamRepository.findById(1)).thenReturn(Optional.of(team1));
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee1));
        when(employeeRepository.findById(2)).thenReturn(Optional.of(employee2));
        when(employeeRepository.findById(3)).thenReturn(Optional.of(employee3));

        assertThrows(IllegalAccessException.class, () -> teamService.unassignEmployees(1, employees));
    }
}