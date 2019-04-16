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

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class DepartmentRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;

    private Department testDepartment;
    private Employee testHead;
    private Team testTeam;
    private List<Employee> testEmployees;

    @Before
    public void init() {

        Employee employee1 = new Employee("Fist1", "Last1", "adress1", "email1@email.com", null, null);
        Employee employee2 = new Employee("Fist2", "Last2", "adress2", "email2@email.com", null, null);

        testHead = employee1;

        employee1 = employeeRepository.save(employee1);
        employee2 = employeeRepository.save(employee2);

        Department department1 = new Department("department1", employee1);
        Department department2 = new Department("department2", employee2);

        department1 = departmentRepository.save(department1);
        department2 = departmentRepository.save(department2);

        testDepartment = departmentRepository.save(department1);
        departmentRepository.save(department2);

        testHead.setDepartment(department1);

        Team team = new Team(department1, null);
        Employee employee3 = new Employee("Fist3", "Last3", "adress3", "email3@email.com", null, department1);
        Employee employee4 = new Employee("Fist4", "Last4", "adress4", "email4@email.com", null, department1);
        Employee employee5 = new Employee("Fist5", "Last5", "adress5", "email5@email.com", null, department1);
        Employee employee6 = new Employee("Fist6", "Last6", "adress6", "email6@email.com", null, department1);
        Employee employee7 = new Employee("Fist7", "Last7", "adress7", "email7@email.com", null, department1);

        testEmployees = (List<Employee>) employeeRepository.saveAll(Arrays.asList(employee1, employee3, employee4, employee5, employee6, employee7));
        team.setLeader(employee3);
        testTeam = teamRepository.save(team);

        testDepartment.setEmployees(testEmployees);
        testDepartment.setTeams(Arrays.asList(testTeam));

        departmentRepository.flush();
    }

    @Test
    public void test_FindByName() {
        List<Department> expected = Arrays.asList(testDepartment);
        List<Department> actual = departmentRepository.findByNameContaining("department1");
        assertEquals(expected, actual);
    }

    @Test
    public void test_FindByHead() {
        List<Department> expected = Arrays.asList(testDepartment);
        List<Department> actual = departmentRepository.findByHead(testHead);
        assertEquals(expected, actual);
    }

    @Test
    public void test_RemoveEmployee(){
        int employeeId = testEmployees.get(3).getId();
        testDepartment.getEmployees().set(3, null);
        departmentRepository.flush();

        assertFalse(employeeRepository.findById(employeeId).isPresent());
    }

    @Test
    public void test_RemoveTeam(){
        int teamId = testTeam.getId();
        testDepartment.getTeams().set(0, null);
        departmentRepository.flush();

        assertFalse(teamRepository.findById(teamId).isPresent());
    }
}
