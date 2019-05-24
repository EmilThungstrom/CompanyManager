package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.forms.DepartmentEmployeeForm;
import se.lexicon.emil.CompanyManager.forms.DepartmentForm;
import se.lexicon.emil.CompanyManager.forms.TeamForm;
import se.lexicon.emil.CompanyManager.service.DepartmentService;
import se.lexicon.emil.CompanyManager.testing.EntityGeneration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DepartmentRestController.class)
@AutoConfigureMockMvc
class DepartmentRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
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
    static Department department11;

    @BeforeAll
    public static void init(){
        department1 = entityGeneration.createDepartment("department1");
        department2 = entityGeneration.createDepartment("department2");
        department3 = entityGeneration.createDepartment("department3");
        department4 = entityGeneration.createDepartment("department4");
        department5 = entityGeneration.createDepartment("department5");
        department11 = entityGeneration.createDepartment("department11");

        department3.setHead(department5.getHead());
    }

    @Test
    void findAll() throws Exception {
        List<Department> departments = Arrays.asList(department1,department2, department3, department4, department5, department11);
        when(departmentService.findAll()).thenReturn(departments);

        mockMvc.perform(get("/api/department"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(departments)));
    }

    @Test
    void findByPartialName() throws Exception {
        List<Department> department = Arrays.asList(department4);
        when(departmentService.findByNameContaining("department4")).thenReturn(department);

        mockMvc.perform(get("/api/department")
                .param("name", "department4"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(department)));

        List<Department> departments = Arrays.asList(department1, department11);
        when(departmentService.findByNameContaining("department11")).thenReturn(departments);

        mockMvc.perform(get("/api/department")
                .param("name", "department11"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(departments)));
    }

    @Test
    void findById() throws Exception {
        when(departmentService.findById(4)).thenReturn(department4);

        mockMvc.perform(get("/api/department")
                .param("id", "4"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(department4)));
    }

    @Test
    void listAll() throws Exception {
        List<Department> departments = Arrays.asList(department1,department2, department3, department4, department5, department11);
        when(departmentService.findAll()).thenReturn(departments);

        Map<String, Integer> departmentNameId = departments.stream().collect(Collectors.toMap(department -> department.getName(), department -> department.getId()));

        mockMvc.perform(get("/api/department/list"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(departmentNameId)));
    }

    @Test
    void getDepartmentTeams() throws Exception {

        Department mockDepartment1 = Mockito.mock(Department.class);
        when(mockDepartment1.getId()).thenReturn(1);
        when(mockDepartment1.getTeams()).thenReturn(department1.getTeams());
        Department mockDepartment2 = Mockito.mock(Department.class);
        when(mockDepartment2.getId()).thenReturn(2);
        when(mockDepartment2.getTeams()).thenReturn(department2.getTeams());
        Department mockDepartment3 = Mockito.mock(Department.class);
        when(mockDepartment3.getId()).thenReturn(3);
        when(mockDepartment3.getTeams()).thenReturn(department3.getTeams());

        List<Department> departments = Arrays.asList(mockDepartment1, mockDepartment2, mockDepartment3);
        when(departmentService.findAll()).thenReturn(departments);

        Map<Integer, List<Integer>> departmentTeams =
                departments.stream().collect(Collectors.toMap(department -> department.getId(), department -> department.getTeams().stream().map(team -> team.getId()).collect(Collectors.toList())));

        mockMvc.perform(get("/api/department/teams"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(departmentTeams)));
    }

    @Test
    void createDepartment() throws Exception {
        Department department = entityGeneration.createDepartment("department");
        when(departmentService.createDepartment("department")).thenReturn(department);

        DepartmentForm departmentForm = new DepartmentForm();
        departmentForm.name = "department";

        mockMvc.perform(post("/api/department/create")
                .content(objectMapper.writeValueAsString(departmentForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(department)));
    }

    @Test
    void deleteDepartment() throws Exception {
        mockMvc.perform(post("/api/department/delete")
                .content("4")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(departmentService).deleteDepartment(4);
    }

    @Test
    void assignEmployees() throws Exception {
        DepartmentEmployeeForm departmentEmployeeForm = new DepartmentEmployeeForm();
        departmentEmployeeForm.departmentId = 3;
        departmentEmployeeForm.employeeIds = new int[]{23, 104, 202, 11, 333};

        mockMvc.perform(post("/api/department/employee/assign")
                .content(objectMapper.writeValueAsString(departmentEmployeeForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(departmentService).assignEmployees(departmentEmployeeForm.departmentId, departmentEmployeeForm.employeeIds);
    }

    @Test
    void removeEmployees() throws Exception {
        DepartmentEmployeeForm departmentEmployeeForm = new DepartmentEmployeeForm();
        departmentEmployeeForm.departmentId = 3;
        departmentEmployeeForm.employeeIds = new int[]{23, 104, 202, 11, 333};

        mockMvc.perform(post("/api/department/employee/delete")
                .content(objectMapper.writeValueAsString(departmentEmployeeForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(departmentService).deleteEmployees(departmentEmployeeForm.departmentId, departmentEmployeeForm.employeeIds);
    }

    @Test
    void addTeam() throws Exception {
        TeamForm teamForm = new TeamForm();
        teamForm.departmentId = 4;

        mockMvc.perform(post("/api/department/team/add")
                .content(objectMapper.writeValueAsString(teamForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(departmentService).addTeam(teamForm.departmentId);
    }

    @Test
    void removeTeam() throws Exception {
        TeamForm teamForm = new TeamForm();
        teamForm.departmentId = 4;
        teamForm.teamId = 5;

        mockMvc.perform(post("/api/department/team/delete")
                .content(objectMapper.writeValueAsString(teamForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(departmentService).deleteTeam(teamForm.departmentId, teamForm.teamId);
    }
}