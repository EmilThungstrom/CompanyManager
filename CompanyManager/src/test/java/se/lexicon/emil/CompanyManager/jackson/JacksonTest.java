package se.lexicon.emil.CompanyManager.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.testing.EntityGeneration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@JsonTest
public class JacksonTest {

    @Autowired
    ObjectMapper objectMapper;

    static EntityGeneration entityGeneration;

    static {
        try {
            entityGeneration = new EntityGeneration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Department department;
    static Team team;

    static Employee head;
    static Employee leader;
    static Employee employee;

    @BeforeAll
    static void init(){
        department = entityGeneration.createDepartment("department");
        team = department.getTeams().get(0);

        head = department.getHead();
        leader = team.getLeader();
        employee = department.getEmployees().get(2);
    }

    @Test
    void testParseDepartment() throws Exception{
        JSONObject jsonDepartment = new JSONObject(objectMapper.writeValueAsString(department));
        assertTrue(jsonDepartment.has("id"));
        assertEquals(jsonDepartment.getString("name"), department.getName());

        JSONObject jsonDepartmentHead = jsonDepartment.getJSONObject("head");
        assertTrue(jsonDepartmentHead.has("id"));
        assertTrue(jsonDepartmentHead.has("firstName"));
        assertTrue(jsonDepartmentHead.has("lastName"));
        assertTrue(jsonDepartmentHead.has("address"));
        assertTrue(jsonDepartmentHead.has("email"));
        assertTrue(jsonDepartmentHead.has("team"));

        JSONArray jsonDepartmentEmployees = jsonDepartment.getJSONArray("employees");
        assertEquals(jsonDepartmentEmployees.length(), department.getEmployees().size());

        JSONObject jsonDepartmentEmployee = jsonDepartmentEmployees.getJSONObject(2);
        assertTrue(jsonDepartmentEmployee.has("id"));
        assertTrue(jsonDepartmentEmployee.has("firstName"));
        assertTrue(jsonDepartmentEmployee.has("lastName"));
        assertTrue(jsonDepartmentEmployee.has("address"));
        assertTrue(jsonDepartmentEmployee.has("email"));
        assertTrue(jsonDepartmentEmployee.has("team"));

        JSONArray jsonDepartmentTeams = jsonDepartment.getJSONArray("teams");
        assertEquals(jsonDepartmentTeams.length(), department.getTeams().size());

        JSONObject jsonDepartmentTeam = jsonDepartmentTeams.getJSONObject(0);
        assertTrue(jsonDepartmentTeam.has("id"));
        assertTrue(jsonDepartmentTeam.has("leader"));
        assertTrue(jsonDepartmentTeam.has("members"));
    }

    @Test
    void testParseEmployee() throws Exception {
        JSONObject jsonEmployee = new JSONObject(objectMapper.writeValueAsString(employee));
        assertTrue(jsonEmployee.has("id"));
        assertEquals(jsonEmployee.getString("firstName"), employee.getFirstName());
        assertEquals(jsonEmployee.getString("lastName"), employee.getLastName());
        assertEquals(jsonEmployee.getString("address"), employee.getAddress());
        assertEquals(jsonEmployee.getString("email"), employee.getEmail());

        JSONObject jsonEmployeeDepartment = jsonEmployee.getJSONObject("department");
        assertTrue(jsonEmployeeDepartment.has("id"));
        assertTrue(jsonEmployeeDepartment.has("name"));

        JSONObject jsonEmployeeTeam = jsonEmployee.getJSONObject("team");
        assertTrue(jsonEmployeeTeam.has("id"));

        JSONObject jsonEmployeeTeamLeader = jsonEmployeeTeam.getJSONObject("leader");
        assertTrue(jsonEmployeeTeamLeader.has("id"));
        assertTrue(jsonEmployeeTeamLeader.has("firstName"));
        assertTrue(jsonEmployeeTeamLeader.has("lastName"));
        assertTrue(jsonEmployeeTeamLeader.has("address"));
        assertTrue(jsonEmployeeTeamLeader.has("email"));
    }

    @Test
    void testParseTeam() throws Exception {
        JSONObject jsonTeam = new JSONObject(objectMapper.writeValueAsString(team));
        assertTrue(jsonTeam.has("id"));

        JSONObject jsonTeamDepartment = jsonTeam.getJSONObject("department");
        assertTrue(jsonTeamDepartment.has("id"));
        assertTrue(jsonTeamDepartment.has("name"));

        JSONObject jsonTeamLeader = jsonTeam.getJSONObject("leader");
        assertTrue(jsonTeamLeader.has("id"));
        assertTrue(jsonTeamLeader.has("firstName"));
        assertTrue(jsonTeamLeader.has("lastName"));
        assertTrue(jsonTeamLeader.has("address"));
        assertTrue(jsonTeamLeader.has("email"));

        JSONArray jsonTeamMembers = jsonTeam.getJSONArray("members");
        assertEquals(jsonTeamMembers.length(), team.getMembers().size());

        JSONObject jsonTeamMember = jsonTeamMembers.getJSONObject(1);
        assertTrue(jsonTeamMember.has("id"));
        assertTrue(jsonTeamMember.has("firstName"));
        assertTrue(jsonTeamMember.has("lastName"));
        assertTrue(jsonTeamMember.has("address"));
        assertTrue(jsonTeamMember.has("email"));
    }
}
