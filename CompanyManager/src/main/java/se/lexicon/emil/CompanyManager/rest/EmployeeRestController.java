package se.lexicon.emil.CompanyManager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Employee>> findall() {
        List<Employee> employees = employeeService.findAll();

        if (employees.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping(params = "id")
    public ResponseEntity<Employee> findById(@RequestParam("id") int employeeId) {
        return ResponseEntity.ok(employeeService.findById(employeeId));
    }

    @PostMapping
    public ResponseEntity<List<Employee>> findByForm(@RequestBody EmployeeForm employeeForm) {
        List<Employee> employees = employeeService.findByForm(employeeForm);

        if (employees.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(employees);
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeForm employeeForm) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeForm));
    }

    @PostMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeForm employeeForm) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeForm));
    }

    @PostMapping("/delete")
    public ResponseEntity<Employee> deleteEmployee(@RequestBody int employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }
}
