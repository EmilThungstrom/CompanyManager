package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.forms.EmployeeForm;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    List<Employee> findByForm(EmployeeForm employeeForm);

    Employee findById(int id);

    Employee createEmployee(EmployeeForm employeeForm);

    Employee updateEmployee(EmployeeForm employeeForm);

    void deleteEmployee(int employeeId);
}
