package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();
    List<Department> findByNameContaining(String name);
    List<Department> findByHead(Employee employee);

    Department findById(int id);

    Department addDepartment(String departmentName);
    void deleteDepartment(int id);

    void assignEmployees(int departmentId, int[] employeeIds);
    void deleteEmployees(int departmentId, int[] employeeIds);
}
