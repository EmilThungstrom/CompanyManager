package se.lexicon.emil.CompanyManager.repositoryTests;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private Team testTeam;
    private Employee testLeader;
    private Department testDepartment;

    @Before
    public void init() {

        Employee employee1 = new Employee("Fist1", "Last1", "adress1", "email1@email.com", null, null);
        Employee employee2 = new Employee("Fist2", "Last2", "adress2", "email2@email.com", null, null);

        testLeader = employee1;

        employee1 = employeeRepository.save(employee1);
        employee2 = employeeRepository.save(employee2);

        Department department1 = new Department("department1", null);
        Department department2 = new Department("department2", null);

        testDepartment = department1;

        department1 = departmentRepository.save(department1);
        department2 = departmentRepository.save(department2);


        Team team1 = new Team(department1, employee1);
        Team team2 = new Team(department2, employee2);

        testTeam = teamRepository.save(team1);
        teamRepository.save(team2);
    }

    @Test
    public void test_FindByLeader() {
        List<Team> expected = Arrays.asList(testTeam);
        List<Team> actual = teamRepository.findByLeader(testLeader);
        assertEquals(expected, actual);
    }

    @Test
    public void test_FindByDepartment() {
        List<Team> expected = Arrays.asList(testTeam);
        List<Team> actual = teamRepository.findByDepartment(testDepartment);
        assertEquals(expected, actual);
    }
}
