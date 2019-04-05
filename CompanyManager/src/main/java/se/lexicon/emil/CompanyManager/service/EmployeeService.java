package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    List<Employee> findByFirstName(String name);
    Employee findById(int id);
}
