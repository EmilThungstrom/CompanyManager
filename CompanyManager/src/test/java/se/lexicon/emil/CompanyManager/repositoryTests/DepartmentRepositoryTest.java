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
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class DepartmentRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private Department testDepartment;
    private Employee testHead;

    @Before
    public void init() {

        Employee employee1 = new Employee("Fist1", "Last1", "adress1", "email1", null, null);
        Employee employee2 = new Employee("Fist2", "Last2", "adress2", "email2", null, null);

        testHead = employee1;

        employee1 = employeeRepository.save(employee1);
        employee2 = employeeRepository.save(employee2);

        Department department1 = new Department("department1", employee1);
        Department department2 = new Department("department2", employee2);

        department1 = departmentRepository.save(department1);
        department2 = departmentRepository.save(department2);

        testDepartment = departmentRepository.save(department1);
        departmentRepository.save(department2);
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
}
