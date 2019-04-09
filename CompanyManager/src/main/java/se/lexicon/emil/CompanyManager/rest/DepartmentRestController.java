package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.emil.CompanyManager.Filter;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.forms.DepartmentEmployeeForm;
import se.lexicon.emil.CompanyManager.service.DepartmentService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/department")
public class DepartmentRestController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentRestController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping
    @JsonView(Filter.DepartmentData.class)
    public ResponseEntity<List<Department>> findAll(){
        List<Department> departments = departmentService.findAll();

        if(departments.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(departments);
    }

    @GetMapping(params = "name")
    @JsonView(Filter.DepartmentData.class)
    public ResponseEntity<List<Department>> findByPartialName( @RequestParam("name") String departmentName){
        List<Department> departments = departmentService.findByNameContaining(departmentName);

        if(departments.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(departments);
    }

    @GetMapping(params = "id")
    @JsonView(Filter.DepartmentData.class)
    public ResponseEntity<Department> findById( @RequestParam("id") int id){
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @PostMapping("/delete")
    public ResponseEntity<Department> addDepartment(@RequestBody int id){
        try{
            departmentService.deleteDepartment(id);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Department> createDepartment(@RequestBody String name){
        return ResponseEntity.ok(departmentService.addDepartment(name));
    }

    @PostMapping("/employee/assign")
    public ResponseEntity assignEmployees(@RequestBody DepartmentEmployeeForm departmentEmployeeForm) {
        departmentService.assignEmployees(departmentEmployeeForm.departmentId, departmentEmployeeForm.employeeIds);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/employee/remove")
    public ResponseEntity removeEmployees(@RequestBody DepartmentEmployeeForm departmentEmployeeForm) {
        departmentService.assignEmployees(departmentEmployeeForm.departmentId, departmentEmployeeForm.employeeIds);

        return ResponseEntity.ok().build();
    }
}
