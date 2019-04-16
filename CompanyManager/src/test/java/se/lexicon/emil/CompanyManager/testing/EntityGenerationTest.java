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
        //TODO: clean up data files used to generate employee fields so it wont generate emails of wrong format
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
    }
}