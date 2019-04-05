package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.emil.CompanyManager.Filter;
import se.lexicon.emil.CompanyManager.entity.Employee;
import se.lexicon.emil.CompanyManager.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    @JsonView(Filter.EmployeeData.class)
    public List<Employee> findall() { return employeeService.findAll(); }

    @GetMapping(value = "/employee", params = "firstname")
    @JsonView(Filter.EmployeeData.class)
    public List<Employee> findByFirstName(@RequestParam("firstname") String firstName){
        return employeeService.findByFirstName(firstName);
    }
}
