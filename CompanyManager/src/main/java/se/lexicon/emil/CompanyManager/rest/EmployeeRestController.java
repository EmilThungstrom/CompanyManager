package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.emil.CompanyManager.Filter;
import se.lexicon.emil.CompanyManager.entities.Employee;
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

    @GetMapping("/employees")
    @JsonView(Filter.EmployeeData.class)
    public ResponseEntity<List<Employee>> findall() {
        List<Employee> employees = employeeService.findAll();

        if(employees.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping(value = "/employee", params = "firstname")
    @JsonView(Filter.EmployeeData.class)
    public ResponseEntity<List<Employee>> findByFirstName(@RequestParam("firstname") String firstName){
        List<Employee> employees = employeeService.findByFirstName(firstName);

        if(employees.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(employeeService.findAll());
    }
}
