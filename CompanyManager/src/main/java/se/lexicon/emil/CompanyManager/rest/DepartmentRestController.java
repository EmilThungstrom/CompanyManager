package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.emil.CompanyManager.Filter;
import se.lexicon.emil.CompanyManager.entity.Department;
import se.lexicon.emil.CompanyManager.service.DepartmentService;
import se.lexicon.emil.CompanyManager.service.DepartmentServiceImpl;

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

    @GetMapping("/departments")
    @JsonView(Filter.DepartmentData.class)
    public ResponseEntity<List<Department>> findAll(){
        List<Department> departments = departmentService.findAll();

        if(departments.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(departments);
    }

    @GetMapping(value = "/department", params = "name")
    @JsonView(Filter.DepartmentData.class)
    public ResponseEntity<List<Department>> findByPartialName( @RequestParam("name") String departmentName){
        List<Department> departments = departmentService.findByNameContaining(departmentName);

        if(departments.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(departments);
    }

    @GetMapping(value = "/department", params = "id")
    @JsonView(Filter.DepartmentData.class)
    public ResponseEntity<Department> findById( @RequestParam("id") int id){
        return ResponseEntity.ok(departmentService.findById(id));
    }
}
