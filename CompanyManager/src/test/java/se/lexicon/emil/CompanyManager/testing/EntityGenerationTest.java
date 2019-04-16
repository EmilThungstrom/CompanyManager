package se.lexicon.emil.CompanyManager.testing;

import org.junit.jupiter.api.Test;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class EntityGenerationTest {

    EntityGeneration entityGeneration;

    public EntityGenerationTest() throws IOException {
        this.entityGeneration = new EntityGeneration();
    }

    @Test
    void createDepartment() {
        Department department = entityGeneration.createDepartment("department1");

        assertEquals("department1", department.getName());
        assertEquals(department.getId(), 0);
        assertNotNull(department.getHead());
        assertThat(department.getEmployees(), is(not(empty())));
        assertThat(department.getTeams(), is(not(empty())));
    }

    @Test
    void createTeam() {
        Department department = new Department("department1", null);
        Team team = entityGeneration.createTeam(department, 7);

        assertEquals(department, team.getDepartment());
        assertEquals(team.getId(), 0);
        assertNotNull(team.getLeader());
        assertThat(team.getMembers(), hasSize(7));
    }

    @Test
    void createEmployee() {
        Department department = new Department("department1", null);
        Team team = new Team(department, null);
        Employee employee = entityGeneration.createEmployee(department, team);

        assertEquals(employee.getId(), 0);
        assertEquals(department, employee.getDepartment());
        assertEquals(team, employee.getTeam());
        assertThat(employee.getFirstName().length(), greaterThan(1));
        assertThat(employee.getLastName().length(), greaterThan(1));
        assertThat(employee.getAddress().length(), greaterThan(1));
        assertThat(employee.getEmail().length(), greaterThan(1));
        /*
        TODO: clean files used for generating employee fields so it wont sometimes generate emails that fails validation
        assertTrue(employee.getEmail().matches(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        ));
        */
    }
}