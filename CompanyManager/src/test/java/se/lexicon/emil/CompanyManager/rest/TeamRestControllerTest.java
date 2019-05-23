package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.service.TeamService;
import se.lexicon.emil.CompanyManager.testing.EntityGeneration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TeamRestController.class)
@AutoConfigureMockMvc
class TeamRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
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

        team2.setLeader(leader);
        team4.setLeader(leader);
        team5.setLeader(leader);
    }

    @Test
    void findAll() throws Exception {
        List<Team> teams = Arrays.asList(team1, team2, team3, team4, team5);
        when(teamService.findAll()).thenReturn(teams);
        mockMvc.perform(get("/api/team"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(teams)));
    }

    @Test
    void findById() throws Exception {
        when(teamService.findById(4)).thenReturn(team4);
        mockMvc.perform(get("/api/team")
                .param("teamId", "4"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(team4)));
    }

    @Test
    void findByLeader() throws Exception {
        List<Team> teams = Arrays.asList(team2, team4, team5);
        when(teamService.findByLeader(1)).thenReturn(teams);
        mockMvc.perform(get("/api/team")
                .param("leaderId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(teams)));
    }

    @Test
    void findByDepartment() throws Exception {
        List<Team> teams = Arrays.asList(team1, team3);
        when(teamService.findByDepartment(1)).thenReturn(teams);
        mockMvc.perform(get("/api/team")
                .param("departmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(teams)));
    }

    @Test
    void changeDepartment() {
    }

    @Test
    void assignEmployees() {
    }

    @Test
    void unassignEmployees() {
    }
}