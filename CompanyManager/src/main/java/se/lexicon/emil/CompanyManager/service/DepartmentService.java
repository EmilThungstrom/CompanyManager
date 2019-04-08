package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entity.Department;
import se.lexicon.emil.CompanyManager.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<Department> findAll();
    List<Department> findByNameContaining(String name);
    List<Department> findByHead(Employee employee);

    Department findById(int id);
}
