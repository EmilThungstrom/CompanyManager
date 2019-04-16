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
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TeamRepository teamRepository;

    private Employee testEmployee;
    private Department testDepartment;
    private Team testTeam;

    @Before
    public void init() {

        Department department1 = new Department("department1", null);
        Department department2 = new Department("department2", null);

        testDepartment = department1;

        department1 = departmentRepository.save(department1);
        department2 = departmentRepository.save(department2);

        Team team1 = new Team(null, null);
        Team team2 = new Team(null, null);

        testTeam = team1;

        team1 = teamRepository.save(team1);
        team2 = teamRepository.save(team2);

        Employee employee1 = new Employee("First1", "Last1", "address1", "email1@email.com", team1, department1);
        Employee employee2 = new Employee("First2", "Last2", "address2", "email2@email.com", team2, department2);

        testEmployee = employeeRepository.save(employee1);
        employeeRepository.save(employee2);
    }

    @Test
    public void test_GetByFirstName() {
        List<Employee> expected = Arrays.asList(testEmployee);
        List<Employee> actual = employeeRepository.findByFirstNameIgnoreCase("First1");
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByLastName() {
        List<Employee> expected = Arrays.asList(testEmployee);
        List<Employee> actual = employeeRepository.findByLastNameIgnoreCase("Last1");
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByAddress() {
        List<Employee> expected = Arrays.asList(testEmployee);
        List<Employee> actual = employeeRepository.findByAddressIgnoreCase("address1");
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByEmail() {
        List<Employee> expected = Arrays.asList(testEmployee);
        List<Employee> actual = employeeRepository.findByEmailIgnoreCase("email1@email.com");
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByDepartment() {
        List<Employee> expected = Arrays.asList(testEmployee);
        List<Employee> actual = employeeRepository.findByDepartment(testDepartment);
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByTeam() {
        List<Employee> expected = Arrays.asList(testEmployee);
        List<Employee> actual = employeeRepository.findByTeam(testTeam);
        assertEquals(expected, actual);
    }
}
