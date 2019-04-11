package se.lexicon.emil.CompanyManager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.forms.DepartmentEmployeeForm;
import se.lexicon.emil.CompanyManager.forms.TeamForm;
import se.lexicon.emil.CompanyManager.service.DepartmentService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/department")
public class DepartmentRestController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentRestController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> findAll() {
        List<Department> departments = departmentService.findAll();

        if (departments.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(departments);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<Department>> findByPartialName(@RequestParam("name") String departmentName) {
        return ResponseEntity.ok(departmentService.findByNameContaining(departmentName));
    }

    @GetMapping(params = "id")
    public ResponseEntity<Department> findById(@RequestParam("id") int id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @PostMapping("/delete")
    public ResponseEntity<Department> addDepartment(@RequestBody int id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Department> createDepartment(@RequestBody String name) {
        return ResponseEntity.ok(departmentService.addDepartment(name));
    }

    @PostMapping("/employee/assign")
    public ResponseEntity assignEmployees(@RequestBody DepartmentEmployeeForm departmentEmployeeForm) {
        departmentService.assignEmployees(departmentEmployeeForm.departmentId, departmentEmployeeForm.employeeIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/employee/delete")
    public ResponseEntity removeEmployees(@RequestBody DepartmentEmployeeForm departmentEmployeeForm) throws IllegalAccessException {
        departmentService.deleteEmployees(departmentEmployeeForm.departmentId, departmentEmployeeForm.employeeIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/team/add")
    public ResponseEntity addTeam(@RequestBody TeamForm teamForm) {
        departmentService.addTeam(teamForm.departmentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/team/delete")
    public ResponseEntity removeTeam(@RequestBody TeamForm teamForm) throws IllegalAccessException {
        departmentService.deleteTeam(teamForm.departmentId, teamForm.teamId);
        return ResponseEntity.ok().build();
    }
}
