package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();
    List<Department> findByNameContaining(String name);
    Department findById(int id);
}
