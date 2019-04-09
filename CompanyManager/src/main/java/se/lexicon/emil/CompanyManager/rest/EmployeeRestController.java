package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.emil.CompanyManager.Filter;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.forms.EmployeeForm;
import se.lexicon.emil.CompanyManager.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

    EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @JsonView(Filter.EmployeeData.class)
    public ResponseEntity<List<Employee>> findall() {
        List<Employee> employees = employeeService.findAll();

        if(employees.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping
    @JsonView(Filter.EmployeeData.class)
    public ResponseEntity<List<Employee>> findByForm(@RequestBody EmployeeForm employeeForm){
        List<Employee> employees = employeeService.findByForm(employeeForm);

        if(employees.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(employees);
    }
}
